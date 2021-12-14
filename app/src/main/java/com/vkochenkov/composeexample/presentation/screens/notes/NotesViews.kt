package com.vkochenkov.composeexample.presentation.screens.notes

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.vkochenkov.composeexample.data.entity.NoteEntity

@Composable
fun NotesScreen() {

    val viewModel = viewModel<NotesViewModel>()
    val state by viewModel.screenState.observeAsState()

    when (state) {
        is NotesScreenState.Standard -> RegularView(state as NotesScreenState.Standard)
        is NotesScreenState.Loading -> LoadingView((state as NotesScreenState.Loading).previousSate)
        is NotesScreenState.Error -> ErrorView(state as NotesScreenState.Error)
        else -> throw IllegalStateException()
    }
}

@Composable
fun ErrorView(screenState: NotesScreenState.Error) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = screenState.error)
    }
}

@Composable
fun LoadingView(previousState: NotesScreenState?) {
    ShowPreviousState(previousState)
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CircularProgressIndicator()
    }
}

@Composable
private fun ShowPreviousState(previousState: NotesScreenState?) {
    when (previousState) {
        is NotesScreenState.Standard -> RegularView(previousState)
        is NotesScreenState.Error -> ErrorView(previousState)
        else -> throw IllegalStateException()
    }
}

@Composable
fun RegularView(screenState: NotesScreenState.Standard) {
    var textFromField by remember { mutableStateOf("") }

    Column() {
        Text(modifier = Modifier.fillMaxWidth(), text = "this is my app with compose")
        TextField(
            value = textFromField,
            onValueChange = { textFromField = it },
            label = { Text("Label") }
        )
        Button(onClick = {
            screenState.addNoteBtnAction(
                NoteEntity(title = textFromField)
            )
        }) {
            //todo static text to resources
            Text(
                text = "add"
            )
        }
        ListView(screenState)
    }
}

@Composable
private fun ListView(screenState: NotesScreenState.Standard) {
    if (screenState.notesList.isEmpty()) {
        //todo improve ui
        Text(text = "empty tables")
    } else {
        LazyColumn() {
            for (note in screenState.notesList) {
                item {
                    ListItemView(note = note, btnAction = { screenState.noteItemBtnAction(note) })
                }
            }
        }
    }
}

@Composable
private fun ListItemView(note: NoteEntity, btnAction: () -> Unit) {
    //todo improve ui
    Card() {
        Row(modifier = Modifier.padding(8.dp)) {
            Text(text = note.title)
            Button(onClick = btnAction) {
                //todo text to resources
                Text(text = "delete")
            }
        }
    }
}

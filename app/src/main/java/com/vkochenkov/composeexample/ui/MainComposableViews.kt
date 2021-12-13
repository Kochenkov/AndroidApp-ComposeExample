package com.vkochenkov.composeexample.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vkochenkov.composeexample.data.entity.NoteEntity
import com.vkochenkov.composeexample.domain.MainActivityViewModel
import com.vkochenkov.composeexample.domain.MainScreenState
import com.vkochenkov.composeexample.ui.theme.ComposeExampleTheme

@Composable
fun MainView(viewModel: MainActivityViewModel) {

    val state by viewModel.screenState.observeAsState()

    ComposeExampleTheme {
        Surface(color = MaterialTheme.colors.background) {
            when (state) {
                is MainScreenState.Regular -> RegularView(screenState = state as MainScreenState.Regular)
                is MainScreenState.Loading -> LoadingView(previousState = (state as MainScreenState.Loading).previousSate)
                is MainScreenState.Error -> ErrorView(screenState = state as MainScreenState.Error)
                else -> throw IllegalStateException()
            }
        }
    }
}

@Composable
fun ErrorView(screenState: MainScreenState.Error) {
    //todo improve ui
    Text(text = screenState.error)
}

@Composable
fun LoadingView(previousState: MainScreenState?) {
    ShowPreviousState(previousState)
    CircularProgressIndicator()
}

@Composable
private fun ShowPreviousState(previousState: MainScreenState?) {
    when (previousState) {
        is MainScreenState.Regular -> RegularView(screenState = previousState)
        is MainScreenState.Error -> ErrorView(screenState = previousState)
        else -> throw IllegalStateException()
    }
}

@Composable
fun RegularView(screenState: MainScreenState.Regular) {
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
private fun ListView(screenState: MainScreenState.Regular) {
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

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
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.vkochenkov.composeexample.data.entity.NoteEntity

@Composable
fun NotesScreen() {
    val viewModel = viewModel<NotesViewModel>()
    val state by viewModel.screenState.observeAsState()

    viewModel.getAllNotes()

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

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            AddNoteItem(screenState = screenState)
        }
        if (screenState.notesList.isEmpty()) {
            item {
                Text(text = "Your have no notes yet. Add someone", fontSize = 20.sp, textAlign = TextAlign.Center)
            }
        } else {
            item {
                Text(text = "Your notes:", fontSize = 20.sp)
                Spacer(modifier = Modifier.padding(8.dp))
            }
            for (note in screenState.notesList) {
                item {
                    NoteItem(note = note, btnAction = { screenState.noteItemBtnAction(note) })
                }
            }
        }
    }
}

@Composable
private fun AddNoteItem(screenState: NotesScreenState.Standard) {
    var noteTitle by remember { mutableStateOf("") }
    var noteDescription by remember { mutableStateOf("") }
    val focusManager = LocalFocusManager.current

    Spacer(modifier = Modifier.padding(8.dp))
    Text(text = "Create your note", fontSize = 20.sp, textAlign = TextAlign.Center)
    Spacer(modifier = Modifier.padding(8.dp))
    OutlinedTextField(
        singleLine = true,
        value = noteTitle,
        onValueChange = {
            if (it.length <= 30) noteTitle = it
        },
        label = { Text("Title (30 digit max)") }
    )
    OutlinedTextField(
        maxLines = 4,
        value = noteDescription,
        onValueChange = {
            if (it.length <= 300) noteDescription = it
        },
        label = { Text("Description") }
    )
    Spacer(modifier = Modifier.padding(8.dp))
    Button(
        enabled = noteTitle.isNotEmpty(),
        onClick = {
            screenState.addNoteBtnAction(
                NoteEntity(title = noteTitle, description = noteDescription)
            )
            noteTitle = ""
            noteDescription = ""
            focusManager.clearFocus()
        }) {
        Text(
            text = "Add"
        )
    }
    Spacer(modifier = Modifier.padding(8.dp))
}

@Composable
private fun NoteItem(note: NoteEntity, btnAction: () -> Unit) {
    Card(modifier = Modifier.fillMaxSize()) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp)
        ) {
            Column(
                modifier = Modifier
                    .weight(4.5f)
                    .padding(4.dp)
            ) {
                Text(
                    text = note.title,
                    fontSize = 18.sp
                )
                Text(
                    text = note.description,
                    fontSize = 16.sp,
                    fontStyle = FontStyle.Italic
                )
            }
            Button(
                modifier = Modifier
                    .weight(1f)
                    .padding(4.dp)
                    .align(Alignment.CenterVertically),
                onClick = btnAction
            ) {
                Icon(Icons.Default.Delete, contentDescription = "delete")
            }
        }
    }
    Spacer(modifier = Modifier.padding(4.dp))
}

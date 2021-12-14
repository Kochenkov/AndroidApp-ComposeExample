package com.vkochenkov.composeexample.presentation.screens.notes

import com.vkochenkov.composeexample.data.entity.NoteEntity

sealed class NotesScreenState {

    data class Standard(
        val notesList: List<NoteEntity> = listOf(),
        val addNoteBtnAction: (note: NoteEntity) -> Unit = {},
        val noteItemBtnAction: (note: NoteEntity) -> Unit = {},
        val message: String? = null
    ) : NotesScreenState()

    data class Error(
        val error: String
    ) : NotesScreenState()

    data class Loading(
        val previousSate: NotesScreenState?
    ) : NotesScreenState()
}

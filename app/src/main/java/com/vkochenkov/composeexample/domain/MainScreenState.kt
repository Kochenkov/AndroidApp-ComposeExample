package com.vkochenkov.composeexample.domain

import com.vkochenkov.composeexample.data.entity.NoteEntity

sealed class MainScreenState {

    data class FillData(
        val notesList: List<NoteEntity> = listOf(),
        val title: String = "Add new note",
        val addNoteBtnAction: (note: NoteEntity) -> Unit = {},
        val noteItemBtnAction: (note: NoteEntity) -> Unit = {}
    ) : MainScreenState()

    data class Error(
        val error: String
    ) : MainScreenState()

    data class Loading(
        val previousSate: MainScreenState?
    ) : MainScreenState()
}

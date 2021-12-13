package com.vkochenkov.composeexample.domain

import com.vkochenkov.composeexample.data.entity.NoteEntity

sealed class MainScreenState {

    data class Regular(
        val notesList: List<NoteEntity> = listOf(),
        val addNoteBtnAction: (note: NoteEntity) -> Unit = {},
        val noteItemBtnAction: (note: NoteEntity) -> Unit = {},
        val message: String? = null
    ) : MainScreenState()

    data class Error(
        val error: String
    ) : MainScreenState()

    data class Loading(
        val previousSate: MainScreenState?
    ) : MainScreenState()
}

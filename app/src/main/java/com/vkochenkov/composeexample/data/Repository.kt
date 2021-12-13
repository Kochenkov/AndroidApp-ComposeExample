package com.vkochenkov.composeexample.data

import com.vkochenkov.composeexample.data.entity.NoteEntity
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class Repository
@Inject constructor(
    private val noteDao: NoteDao
) {

    fun getAllNotes(): Maybe<List<NoteEntity>> {
        return noteDao.getAllNotes()
            .subscribeOn(Schedulers.io())
    }

    fun insertNote(noteEntity: NoteEntity): Completable {
        return noteDao.insertNote(noteEntity)
            .subscribeOn(Schedulers.io())
    }

    fun deleteNote(noteEntity: NoteEntity): Completable {
        return noteDao.deleteNote(noteEntity)
            .subscribeOn(Schedulers.io())
    }
}
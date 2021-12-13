package com.vkochenkov.composeexample.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.vkochenkov.composeexample.data.entity.NoteEntity
import io.reactivex.Completable
import io.reactivex.Maybe

@Dao
interface NoteDao {

    @Query("SELECT * FROM note_entity ORDER BY id DESC")
    fun getAllNotes() : Maybe<List<NoteEntity>>

    @Insert
    fun insertNote(noteEntity: NoteEntity) : Completable

    @Delete
    fun deleteNote(noteEntity: NoteEntity) : Completable
}

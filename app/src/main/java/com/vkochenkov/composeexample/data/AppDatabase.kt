package com.vkochenkov.composeexample.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.vkochenkov.composeexample.data.entity.NoteEntity

@Database(entities = [NoteEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getNoteDao() : NoteDao

    companion object {
        private var dbInstance: AppDatabase? = null

        fun getAppDatabaseInstance(context: Context): AppDatabase {
            var thisDbInstance = dbInstance
            if (thisDbInstance == null) {
                thisDbInstance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_db"
                ).build()
                dbInstance = thisDbInstance
            }
            return thisDbInstance
        }
    }
}
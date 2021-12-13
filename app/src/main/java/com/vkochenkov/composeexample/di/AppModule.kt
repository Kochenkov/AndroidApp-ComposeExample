package com.vkochenkov.composeexample.di

import android.app.Application
import android.content.Context
import com.vkochenkov.composeexample.data.AppDatabase
import com.vkochenkov.composeexample.data.NoteDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(val application: Application) {

    @Singleton
    @Provides
    fun provideAppContext() : Context {
        return application.applicationContext
    }

    @Singleton
    @Provides
    fun getRoomDbInstance(context: Context): AppDatabase {
        return AppDatabase.getAppDatabaseInstance(context)
    }

    @Singleton
    @Provides
    fun getNoteDao(appDatabase: AppDatabase): NoteDao {
        return appDatabase.getNoteDao()
    }
}
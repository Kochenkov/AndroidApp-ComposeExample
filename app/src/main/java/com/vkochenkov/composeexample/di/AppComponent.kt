package com.vkochenkov.composeexample.di

import com.vkochenkov.composeexample.presentation.screens.notes.NotesViewModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {

    fun inject(mainViewModel: NotesViewModel)
}
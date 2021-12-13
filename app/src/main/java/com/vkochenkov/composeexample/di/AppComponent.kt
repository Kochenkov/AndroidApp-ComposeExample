package com.vkochenkov.composeexample.di

import com.vkochenkov.composeexample.domain.MainActivityViewModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {

    fun inject(mainActivityViewModel: MainActivityViewModel)
}
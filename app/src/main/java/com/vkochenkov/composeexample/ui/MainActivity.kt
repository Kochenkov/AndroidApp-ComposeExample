package com.vkochenkov.composeexample.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.ViewModelProvider
import com.vkochenkov.composeexample.domain.MainActivityViewModel

class MainActivity : ComponentActivity() {

    companion object {
        var activityFirstLaunch = true
    }

    private val viewModel by lazy {
        ViewModelProvider(this).get(MainActivityViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (activityFirstLaunch) {
            viewModel.getAllNotes()
            activityFirstLaunch = false
        }

        setContent {
            MainView(viewModel)
        }
    }
}
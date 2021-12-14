package com.vkochenkov.composeexample.presentation.navigation

import com.vkochenkov.composeexample.R

enum class BottomBarItem(
    val title: Int,
    val iconId: Int,
    val rout: String
) {
    Notes(R.string.notes_bottom_str, R.drawable.ic_launcher_foreground, "/notes"),
    Info(R.string.info_bottom_str, R.drawable.ic_launcher_foreground, "/info")
}
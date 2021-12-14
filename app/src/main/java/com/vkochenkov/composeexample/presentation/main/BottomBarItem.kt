package com.vkochenkov.composeexample.presentation.main

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.List
import androidx.compose.ui.graphics.vector.ImageVector
import com.vkochenkov.composeexample.R

enum class BottomBarItem(
    val title: Int,
    val icon: ImageVector,
    val rout: String
) {
    Notes(R.string.notes_bottom_str, Icons.Filled.List, "/notes"),
    Info(R.string.info_bottom_str, Icons.Filled.Info, "/info")
}
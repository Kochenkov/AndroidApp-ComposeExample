package com.vkochenkov.composeexample.presentation.main

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.List
import androidx.compose.ui.graphics.vector.ImageVector
import com.vkochenkov.composeexample.R

enum class BottomBarItem(
    val title: Int,
    val icon: ImageVector,
    val route: NavigationRoute
) {
    Notes(R.string.notes_bottom_str, Icons.Filled.List, NavigationRoute.Notes),
    Info(R.string.info_bottom_str, Icons.Filled.Info, NavigationRoute.Info)
}
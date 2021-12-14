package com.vkochenkov.composeexample.presentation.main

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.vkochenkov.composeexample.presentation.screens.info.InfoScreen
import com.vkochenkov.composeexample.presentation.screens.notes.NotesScreen
import com.vkochenkov.composeexample.presentation.screens.other.OtherScreen
import com.vkochenkov.composeexample.presentation.ui.theme.ComposeExampleTheme

@Composable
fun AppContent() {

    val bottomBarItems = listOf(BottomBarItem.Notes, BottomBarItem.Info)
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route?.substringBeforeLast("/")

    ComposeExampleTheme {
        Surface(color = MaterialTheme.colors.background) {
            Scaffold(
                bottomBar = {
                    if (currentRoute == MAIN_PREFIX) {
                        BottomNavBar(bottomBarItems, navController, navBackStackEntry)
                    }
                }
            ) { paddingValues ->
                AppNavigation(
                    navController = navController,
                    bottomBarItems = bottomBarItems,
                    paddingValues = paddingValues,
                )
            }
        }
    }
}

@Composable
fun AppNavigation(
    navController: NavHostController,
    bottomBarItems: List<BottomBarItem>,
    paddingValues: PaddingValues
) {
    NavHost(
        navController = navController,
        startDestination = bottomBarItems[0].route.value,
        modifier = Modifier.padding(paddingValues)
    ) {
        composable(BottomBarItem.Notes.route.value) {
            NotesScreen()
        }
        composable(BottomBarItem.Info.route.value) {
            InfoScreen(navController)
        }
        composable(NavigationRoute.Other.value) {
            OtherScreen()
        }
    }
}

@Composable
fun BottomNavBar(
    items: List<BottomBarItem>,
    navController: NavHostController,
    navBackStackEntry: NavBackStackEntry?,
) {
    val context = LocalContext.current
    BottomNavigation {
        val currentDestination = navBackStackEntry?.destination
        items.forEach { item ->
            val isSelected = currentDestination?.hierarchy?.any { it.route == item.route.value } == true
            BottomNavigationItem(
                selected = isSelected,
                onClick = {
                    navController.navigate(item.route.value) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                label = { Text(text = context.getString(item.title)) },
                icon = { Icon(item.icon, item.name) },
            )
        }
    }
}
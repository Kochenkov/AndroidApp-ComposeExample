package com.vkochenkov.composeexample.presentation

import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.vkochenkov.composeexample.presentation.navigation.BottomBarItem
import com.vkochenkov.composeexample.presentation.screens.info.InfoScreen
import com.vkochenkov.composeexample.presentation.screens.notes.NotesScreen
import com.vkochenkov.composeexample.presentation.ui.theme.ComposeExampleTheme

@Composable
fun MainView() {
    val context = LocalContext.current
    val bottomBarItems = listOf(BottomBarItem.Notes, BottomBarItem.Info)
    val navController = rememberNavController()

    ComposeExampleTheme {
        Surface(color = MaterialTheme.colors.background) {
            Scaffold(
                topBar = {
                    TopAppBar() {
                        //todo
                    }
                },
                bottomBar = {
                    BottomNavigation {
                        val navBackStackEntry by navController.currentBackStackEntryAsState()
                        val currentDestination = navBackStackEntry?.destination
                        //val previousDestination = remember { mutableStateOf(bottomBarItems.first()) }
                        bottomBarItems.forEach { item ->
                            val isSelected = currentDestination?.hierarchy
                                ?.any { it.route == item.rout } == true
                            BottomNavigationItem(
                                selected = isSelected,
                                onClick = {
                                    //кажется что не нужно
                                    //if (item == previousDestination.value) return@BottomNavigationItem
                                    //previousDestination.value = item
                                    navController.navigate(item.rout) {
                                        popUpTo(navController.graph.findStartDestination().id) {
                                            saveState = true
                                        }
                                        launchSingleTop = true
                                        restoreState = true
                                    }
                                },
                                label = { Text(text = context.getString(item.title)) },
                                icon = {
                                    //todo
                                },
                            )
                        }
                    }
                }
            ) {
                NavHost(navController = navController, startDestination = bottomBarItems[0].rout) {
                    composable(BottomBarItem.Notes.rout) { NotesScreen() }
                    composable(BottomBarItem.Info.rout) { InfoScreen(navController) }
                    //todo сделать открытие полностью другого экрана, без нижнего бара
                    composable("/other") { Text(text = "TEST") }
                }
            }
        }
    }
}
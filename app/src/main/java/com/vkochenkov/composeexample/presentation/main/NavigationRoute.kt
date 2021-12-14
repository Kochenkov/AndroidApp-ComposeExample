package com.vkochenkov.composeexample.presentation.main

const val MAIN_PREFIX = "main"

enum class NavigationRoute(val value: String) {

    /**
     * Use MAIN_PREFIX for navigation on main screen and show bottomNavBar
     */

    Notes("$MAIN_PREFIX/notes"),
    Info("$MAIN_PREFIX/info"),
    Other("/other")
}
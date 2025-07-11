package com.app.kelompok_5_6.navigation

sealed class Screen(val route: String) {

    object Home: Screen(route = "home")

    object Profile: Screen(route = "profile")

}
package org.codeforegypt.core.base

sealed class Screen(val route: String) {
    object HomeScreen : Screen("home_screen")
    object DetailsScreen : Screen("details_screen")

}
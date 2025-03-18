package org.codeforegypt.core.navigation

sealed class Screen(val route: String) {
    object HomeScreen : Screen("home_screen")
    object DetailsScreen : Screen("details_screen")

}
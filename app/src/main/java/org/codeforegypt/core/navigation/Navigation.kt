package org.codeforegypt.core.navigation

import androidx.compose.runtime.Composable


import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import org.codeforegypt.features.feature_home.presentation.screen.HomeScreen
import org.codeforegypt.features.feature_movie_details.presentation.screen.MovieDetailsScreen


@Composable
fun Navigation(
    navController: NavHostController,
    startDestination: String
    ) {
    NavHost(
        navController = navController,
        startDestination = startDestination,

        ) {
        composable(Screen.HomeScreen.route) {
            HomeScreen(navController)

        }
        composable(
            Screen.DetailsScreen.route + "/{movieId}",
            arguments = listOf(navArgument("movieId") { type = NavType.IntType })
        ) { backStackEntry ->
            val movieId = backStackEntry.arguments?.getInt("movieId") ?: 0
            MovieDetailsScreen(movieId)
        }
    }


}
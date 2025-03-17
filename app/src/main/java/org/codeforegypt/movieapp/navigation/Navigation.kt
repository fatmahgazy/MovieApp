package org.codeforegypt.movieapp.navigation

import androidx.compose.runtime.Composable


import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import org.codeforegypt.movieapp.ui.screens.HomeScreen
import org.codeforegypt.movieapp.ui.screens.MovieDetailsScreen
import org.codeforegypt.movieapp.viewModel.DetailsScreenViewModel
import org.codeforegypt.movieapp.viewModel.HomeViewModel


@Composable
fun Navigation(
    navController: NavHostController,
    startDestination: String,
    homeViewModel: HomeViewModel
    ) {
    NavHost(
        navController = navController,
        startDestination = startDestination,

        ) {
        composable(Screen.HomeScreen.route) {
            HomeScreen(navController, homeViewModel)

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
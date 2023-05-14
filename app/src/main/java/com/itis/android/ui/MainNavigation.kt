package com.itis.android.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.itis.android.ui.screens.details.DetailsScreen
import com.itis.android.ui.screens.main.MainScreen

sealed class Screen(
    val route: String,
    val arguments: List<NamedNavArgument> = emptyList(),
) {

    object Main : Screen(route = "main")

    class Details(
        filmId: Int = 0,
        val processedRoute: String = "details/$filmId"
    ) : Screen(
        route = "details/{filmId}",
        arguments = listOf(navArgument("filmId") { type = NavType.IntType })
    )
}

@Composable
fun MainNavigation(
    navController: NavHostController = rememberNavController(),
    startDestination: String = Screen.Main.route
) {
    NavHost(navController = navController, startDestination = startDestination) {
        composable(Screen.Main.route) { MainScreen(navController) }
        composable(Screen.Details().route, Screen.Details().arguments) {
            DetailsScreen(it.arguments?.getInt("filmId"))
        }
    }
}

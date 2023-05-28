package com.itis.android.ui.screens.navhost

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.itis.android.R
import com.itis.android.ui.screens.StubScreen
import com.itis.android.ui.screens.details.DetailsScreen
import com.itis.android.ui.screens.main.MainScreen
import com.itis.android.ui.screens.settings.SettingsScreen
import com.itis.android.ui.theme.Theme

sealed class Screen(
    val icon: ImageVector,
    @StringRes val name: Int = 0,
    val route: String,
    val arguments: List<NamedNavArgument> = emptyList(),
) {

    object Main : Screen(
        route = "main",
        name = R.string.films,
        icon = Icons.Filled.Home,
    )

    object Settings : Screen(
        route = "settings",
        name = R.string.settings,
        icon = Icons.Filled.Settings,
    )

    object Stub : Screen(
        route = "stub",
        name = R.string.stub,
        icon = Icons.Filled.Info,
    )

    class Details(
        filmId: Int = 0,
        val processedRoute: String = "details/$filmId"
    ) : Screen(
        route = "details/{filmId}",
        icon = Icons.Filled.Home,
        arguments = listOf(navArgument("filmId") { type = NavType.IntType }),
    )
}

@Composable
fun MainNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: Screen = Screen.Main
) {
    val items = listOf(
        Screen.Main,
        Screen.Settings,
    )
    Scaffold(
        bottomBar = {
            BottomNavigation(
                backgroundColor = Theme.colors.tintColor
            ) {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination
                items.forEach { screen ->
                    BottomNavigationItem(
                        icon = { Icon(screen.icon, contentDescription = null) },
                        label = { Text(stringResource(screen.name)) },
                        selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                        onClick = {
                            navController.navigate(screen.route) {
                                // Pop up to the start destination of the graph to
                                // avoid building up a large stack of destinations
                                // on the back stack as users select items
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                // Avoid multiple copies of the same destination when
                                // reselecting the same item
                                launchSingleTop = true
                                // Restore state when reselecting a previously selected item
                                restoreState = true
                            }
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController,
            startDestination = startDestination.route,
            Modifier.padding(innerPadding),
        ) {
            composable(Screen.Main.route) { MainScreen(navController) }
            composable(Screen.Settings.route) { SettingsScreen(navController) }
            composable(Screen.Stub.route) { StubScreen() }
            composable(Screen.Details().route, Screen.Details().arguments) {
                DetailsScreen(it.arguments?.getInt("filmId"))
            }
        }
    }
}

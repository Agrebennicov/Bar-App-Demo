package com.example.jetpackdemo.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.jetpackdemo.ui.dashboard.DashboardScreen
import com.example.jetpackdemo.ui.details.DetailsScreen
import com.example.jetpackdemo.ui.search.SearchScreen
import com.example.jetpackdemo.ui.splash.SplashScreen


@Composable
fun NavGraph(
    navController: NavHostController,
    searchValue: String,
) {
    val actions = remember(navController) { NavActions(navController) }

    NavHost(
        navController = navController,
        startDestination = Screen.SPLASH
    ) {

        composable(Screen.SPLASH) {
            SplashScreen(
                onDone = {
                    actions.openDrinksList()
                }
            )
        }

        composable(Screen.DRINKS_LIST) {
            DashboardScreen()
        }
        composable(Screen.DRINKS_SEARCH) {
            SearchScreen(searchValue = searchValue, onDrinkClicked = { id ->
                actions.openDetails(id)
            })
        }
        composable(route = "${Screen.DRINK_DETAILS}/{id}",
            arguments = listOf(navArgument("id") { type = NavType.LongType })
        ) {backStackEntry ->
            DetailsScreen(id = backStackEntry.arguments?.getLong("id") ?: 1)
        }
    }
}
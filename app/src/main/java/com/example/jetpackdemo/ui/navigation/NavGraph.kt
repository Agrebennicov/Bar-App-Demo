package com.example.jetpackdemo.ui.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.jetpackdemo.ui.SharedViewModel
import com.example.jetpackdemo.ui.details.DetailsScreen
import com.example.jetpackdemo.ui.details.DetailsViewModel
import com.example.jetpackdemo.ui.drinks.DrinksScreen
import com.example.jetpackdemo.ui.search.SearchScreen
import com.example.jetpackdemo.ui.search.SearchViewModel
import com.example.jetpackdemo.ui.splash.SplashScreen


@ExperimentalAnimationApi
@Composable
fun NavGraph(
    navController: NavHostController,
    searchValue: String,
    sharedViewModel: SharedViewModel
) {
    val actions = remember(navController) { NavActions(navController) }
    NavHost(
        navController = navController,
        startDestination = Screen.DRINKS_LIST
    ) {
        composable(Screen.SPLASH) {
            SplashScreen(
                onDone = {
                    actions.openDrinksList()

                }
            )
        }

        composable(Screen.DRINKS_LIST) {
            DrinksScreen(
                navigateDrinkDetails = {
                    actions.openDetails(it)
                }
            )
        }

        composable(Screen.DRINKS_SEARCH) {
            val viewModel = hiltViewModel<SearchViewModel>()
            viewModel.search(searchValue)
            val uiState by viewModel.uiState.collectAsState()
            SearchScreen(uiState = uiState, onDrinkClicked = { id ->
                actions.openDetails(id)
            })
        }
        composable(
            route = "${Screen.DRINK_DETAILS}/{id}",
            arguments = listOf(navArgument("id") { type = NavType.LongType })
        ) { backStackEntry ->
            val viewModel = hiltViewModel<DetailsViewModel>()
            val uiState by viewModel.uiState.collectAsState()
            DetailsScreen(uiState) {
                sharedViewModel.drinkName.value = it
            }
        }
    }
}
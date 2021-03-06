package com.example.jetpackdemo.ui.navigation

import androidx.navigation.NavHostController

class NavActions(private val navController: NavHostController) {

    fun openDrinksList() {
        navController.navigate(Screen.DRINKS_LIST) {
            popUpTo(Screen.SPLASH) { inclusive = true }
        }
    }

    fun openSearch() {
        navController.navigate(Screen.DRINKS_SEARCH)
    }

    fun openDetails(id: Long) {
        navController.navigate("${Screen.DRINK_DETAILS}/$id")
    }
}

object Screen {
    const val SPLASH = "splash"
    const val DRINKS_LIST = "drinkList"
    const val DRINK_DETAILS = "drinkDetails"
    const val DRINKS_SEARCH = "drinksSearch"
}
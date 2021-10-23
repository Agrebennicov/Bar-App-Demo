package com.example.jetpackdemo.ui

import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.example.jetpackdemo.ui.navigation.NavGraph
import com.example.jetpackdemo.ui.theme.DrinkTheme

@Composable
fun DrinkApp() {
    DrinkTheme {
        val navController = rememberNavController()

        Scaffold(
            backgroundColor = DrinkTheme.colors.background,
        ) {
            NavGraph(
                navController = navController,
            )
        }
    }
}
package com.example.jetpackdemo.ui

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.jetpackdemo.R
import com.example.jetpackdemo.ui.navigation.NavActions
import com.example.jetpackdemo.ui.navigation.NavGraph
import com.example.jetpackdemo.ui.navigation.Screen
import com.example.jetpackdemo.ui.theme.DrinkTheme

@ExperimentalAnimationApi
@Composable
fun DrinkApp() {
    DrinkTheme {
        val navController = rememberNavController()
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination
        var searchValue by remember { mutableStateOf("") }
        Scaffold(
            backgroundColor = DrinkTheme.colors.background,
            topBar = {
                DrinksTopAppBar(
                    navController = navController,
                    currentRoute = currentDestination?.route,
                    onSearchValueChanged = {
                        searchValue = it
                    })
            }
        ) {
            NavGraph(
                navController = navController,
                searchValue = searchValue
            )
        }
    }
}

@Composable
fun DrinksTopAppBar(
    navController: NavHostController,
    currentRoute: String?,
    onSearchValueChanged: (String) -> Unit
) {
    when (currentRoute) {
        Screen.DRINKS_LIST -> TopAppBar(elevation = 0.dp) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    modifier = Modifier.weight(1f),
                    text = "Demo App",
                    textAlign = TextAlign.Center,
                )
                Image(
                    modifier = Modifier.clickable { NavActions(navController).openSearch() },
                    painter = painterResource(R.drawable.ic_search),
                    contentDescription = "Search"
                )
            }
        }
        Screen.DRINKS_SEARCH -> TopAppBar(elevation = 0.dp) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    modifier = Modifier
                        .padding(end = 12.dp)
                        .clickable { navController.navigateUp() },
                    painter = painterResource(R.drawable.ic_chevron_left),
                    contentDescription = "Back"
                )
                var searchValue by remember { mutableStateOf("") }
                OutlinedTextField(
                    shape = CircleShape,
                    modifier = Modifier.fillMaxWidth(),
                    value = searchValue,
                    placeholder = { Text(text = "Search", color = DrinkTheme.colors.onPrimary) },
                    onValueChange = {
                        searchValue = it
                        onSearchValueChanged(it)
                    },
                    trailingIcon = {
                        Image(
                            modifier = Modifier.clickable { searchValue = "" },
                            painter = painterResource(R.drawable.ic_close),
                            contentDescription = "Delete"
                        )
                    },
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = DrinkTheme.colors.secondary,
                        unfocusedBorderColor = DrinkTheme.colors.secondary
                    )
                )

            }
        }
        else -> null
    }
}


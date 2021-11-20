package com.example.jetpackdemo.ui

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
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

    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val sharedViewModel = hiltViewModel<SharedViewModel>()
    var searchValue by remember { mutableStateOf("") }

    Scaffold(
        backgroundColor = DrinkTheme.colors.background,
        topBar = {
            DrinksTopAppBar(
                navController = navController,
                currentRoute = currentDestination?.route,
                drinkName = sharedViewModel.drinkName.value,
                onSearchValueChanged = {
                    searchValue = it
                })
        }
    ) {
        NavGraph(
            navController = navController,
            searchValue = searchValue,
            sharedViewModel = sharedViewModel
        )
    }
}

@Composable
fun DrinksTopAppBar(
    navController: NavHostController,
    currentRoute: String?,
    drinkName: String,
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
                OutlinedChevron { navController.navigateUp() }
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
        "${Screen.DRINK_DETAILS}/{id}" -> TopAppBar(elevation = 0.dp) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                OutlinedChevron { navController.navigateUp() }

                Text(
                    text = drinkName,
                    textAlign = TextAlign.Center,
                )

                Image(
                    modifier = Modifier.clickable { },
                    painter = painterResource(R.drawable.ic_favorite),
                    contentDescription = "Favorite"
                )
            }
        }
        else -> null
    }
}

@Composable
private fun OutlinedChevron(onClick: () -> Unit) {
    Box(
        Modifier
            .height(34.dp)
            .width(54.dp)
            .offset(x = (-16).dp)
            .border(
                1.dp,
                DrinkTheme.colors.secondary,
                RoundedCornerShape(
                    topEndPercent = 100,
                    bottomEndPercent = 100,
                    topStartPercent = 0,
                    bottomStartPercent = 0
                )
            )
            .clickable { onClick.invoke() },
        contentAlignment = Alignment.CenterEnd
    ) {
        Image(painter = painterResource(R.drawable.ic_chevron_left), contentDescription = "Back")
    }
}

@Preview
@Composable
fun OutlinedChevronPreview() {
    OutlinedChevron { }
}

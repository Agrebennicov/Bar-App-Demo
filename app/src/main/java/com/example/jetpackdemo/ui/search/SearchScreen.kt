package com.example.jetpackdemo.ui.search

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import coil.transform.CircleCropTransformation
import com.example.jetpackdemo.R
import com.example.jetpackdemo.pojo.Drink
import com.example.jetpackdemo.pojo.DrinkCategory
import com.example.jetpackdemo.ui.details.MyDescription
import com.example.jetpackdemo.ui.details.MyLabel
import com.example.jetpackdemo.ui.theme.DrinkTheme

@Composable
fun SearchScreen(uiState: SearchViewModel.SearchState, onDrinkClicked: (Long) -> Unit) {
    Surface {
        Card {
            when (uiState) {
                is SearchViewModel.SearchState.Empty -> Placeholder()

                is SearchViewModel.SearchState.Loaded -> {
                    val drinks = uiState.drinks
                    LazyColumn {
                        items(drinks) { drink ->
                            SearchItem(drink) { onDrinkClicked(it) }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun Placeholder() {
    Column {
        Image(
            painter = painterResource(R.drawable.cocktail),
            contentDescription = "Empty cocktail list"
        )
        Text(text = "Sorry\nNo cocktails were found")
    }
}

@Composable
private fun SearchItem(drink: Drink, onDrinkClicked: (Long) -> Unit) {
    Column {
        Row {
            OutlinedRoundImage(drink.imageUrl, drink.name)
            Column {
                MyLabel(text = drink.name)

                MyDescription(text = drink.category?.categoryName ?: "")
            }
        }
        Divider(color = DrinkTheme.colors.onSurface)
    }
}

@Composable
private fun OutlinedRoundImage(url: String, description: String) {
    Image(
        modifier = Modifier.size(50.dp),
        painter = rememberImagePainter(data = url),
        contentDescription = description
    )
}

@Preview
@Composable
fun SearchScreenEmptyPreview() {
    SearchScreen(uiState = SearchViewModel.SearchState.Empty, onDrinkClicked = {})
}

@Preview
@Composable
fun SearchScreenLoadedPreview() {
    SearchScreen(uiState = SearchViewModel.SearchState.Loaded(
        listOf(
            Drink(
                imageUrl = "https://www.thecocktaildb.com//images//media//drink//metwgh1606770327.jpg",
                name = "Mojito",
                category = DrinkCategory.COCKTAIL
            ),
            Drink(
                imageUrl = "https://www.thecocktaildb.com//images//media//drink//metwgh1606770327.jpg",
                name = "Mojito",
                category = DrinkCategory.COCKTAIL
            ), Drink(
                imageUrl = "https://www.thecocktaildb.com//images//media//drink//metwgh1606770327.jpg",
                name = "Mojito",
                category = DrinkCategory.COCKTAIL
            ), Drink(
                imageUrl = "https://www.thecocktaildb.com//images//media//drink//metwgh1606770327.jpg",
                name = "Mojito",
                category = DrinkCategory.COCKTAIL
            )
        )
    ), onDrinkClicked = {})
}

@Preview
@Composable
fun SearchItemPreview() {
    SearchItem(
        drink = Drink(
            imageUrl = "https://www.thecocktaildb.com//images//media//drink//metwgh1606770327.jpg",
            name = "Mojito",
            category = DrinkCategory.COCKTAIL
        )
    ) {}
}

@Preview
@Composable
fun OutlinedRoundImagePreview() {
    OutlinedRoundImage(
        url = "https://www.thecocktaildb.com//images//media//drink//metwgh1606770327.jpg",
        description = "Mojito",
    )
}

@Preview
@Composable
fun PlaceholderPreview() {
    Placeholder()
}
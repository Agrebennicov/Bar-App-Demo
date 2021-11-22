package com.example.jetpackdemo.ui.details

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.example.jetpackdemo.pojo.AlcoholicType
import com.example.jetpackdemo.pojo.Drink
import com.example.jetpackdemo.pojo.DrinkCategory
import com.example.jetpackdemo.ui.common.CircularLoading
import com.example.jetpackdemo.ui.theme.DrinkTheme

@Composable
fun DetailsScreen(uiState: DetailsViewModel.DetailsState, onDrinkLoaded: (String) -> Unit) {

    Surface {
        Card {
            Column {
            when (uiState) {
                is DetailsViewModel.DetailsState.Loading -> CircularLoading(Modifier.fillMaxSize())

                is DetailsViewModel.DetailsState.Loaded -> {
                    val drink = uiState.drink
                    LaunchedEffect(true) {
                        onDrinkLoaded(drink.name)
                    }
                        Image(
                            painter = rememberImagePainter(data = drink.imageUrl),
                            contentDescription = drink.name
                        )
                            DrinkInfo(drink)

                            DrinkIngredients(drink)

                            DrinkInstructions(drink.instructions ?: "")
                    }
                }
            }
        }
    }
}

@Composable
private fun DrinkInstructions(instructions: String) {
    Text(
        text = "Instructions:"
    )

    Text(text = instructions)
}

@Composable
private fun DrinkIngredients(drink: Drink) {
    Text(
        text = "Ingredients:"
    )

    drink.ingredient1?.let { Text(text = drink.ingredient1) }
    drink.ingredient2?.let { Text(text = drink.ingredient2) }
    drink.ingredient3?.let { Text(text = drink.ingredient3) }
    drink.ingredient4?.let { Text(text = drink.ingredient4) }
    drink.ingredient5?.let { Text(text = drink.ingredient5) }
    drink.ingredient6?.let { Text(text = drink.ingredient6) }
    drink.ingredient7?.let { Text(text = drink.ingredient7) }
    drink.ingredient8?.let { Text(text = drink.ingredient8) }
}

@Composable
private fun DrinkInfo(drink: Drink) {
    Row {
        LabelAndDescription(label = "Category", description = drink.category?.categoryName ?: "")

        LabelAndDescription(label = "Glass", description = drink.glass ?: "")

        LabelAndDescription(label = "Alcoholic", description = drink.alcoholicType?.typeName ?: "")
    }
}

@Composable
private fun LabelAndDescription(label: String, description: String) {
    Column {
        Text(
            text = label,
        )

        Text(
            text = description,
        )
    }
}

@Composable
private fun VerticalDivider() {
    Divider(color = DrinkTheme.colors.onBackground)
}

@Preview
@Composable
fun DrinkInfoPreview() {
    DrinkInfo(
        drink = Drink(
            category = DrinkCategory.COCKTAIL,
            glass = "Highball glass",
            alcoholicType = AlcoholicType.ALCOHOLIC
        )
    )
}

@Preview
@Composable
fun DrinkIngredientsPreview() {
    Column {
        DrinkIngredients(
            drink = Drink(
                ingredient1 = "Light rum",
                ingredient2 = "Lime",
                ingredient3 = "Sugar",
                ingredient4 = "Mint",
                ingredient5 = "Soda water",
            )
        )
    }
}

@Preview
@Composable
fun DrinkInstructionsPreview() {
    Column {
        DrinkInstructions(instructions = "INSTRUCTIONS...")
    }
}

@Preview
@Composable
fun DetailsScreenPreview() {
    DetailsScreen(uiState = DetailsViewModel.DetailsState.Loaded(
        Drink(
            imageUrl = "https://www.thecocktaildb.com//images//media//drink//metwgh1606770327.jpg",
            category = DrinkCategory.COCKTAIL,
            glass = "Highball glass",
            alcoholicType = AlcoholicType.ALCOHOLIC,
            ingredient1 = "Light rum",
            ingredient2 = "Lime",
            ingredient3 = "Sugar",
            ingredient4 = "Mint",
            ingredient5 = "Soda water",
            instructions = "Instructions..."
        )
    ), onDrinkLoaded = {})
}

@Preview
@Composable
fun DetailsScreenLoadingPreview() {
    DetailsScreen(uiState = DetailsViewModel.DetailsState.Loading, onDrinkLoaded = {})
}
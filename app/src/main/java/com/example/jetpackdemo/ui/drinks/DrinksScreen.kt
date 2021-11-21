package com.example.jetpackdemo.ui.drinks

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberImagePainter
import com.example.jetpackdemo.pojo.Drink
import com.example.jetpackdemo.pojo.DrinkCategory
import com.example.jetpackdemo.ui.common.CircularLoading
import com.example.jetpackdemo.ui.common.ErrorMessage
import com.example.jetpackdemo.ui.drinks.DrinksViewModel.DrinksState
import com.example.jetpackdemo.ui.theme.DrinkTheme

@Composable
fun DrinksScreen(
    navigateDrinkDetails: (id: Long) -> Unit
) {
    val viewModel: DrinksViewModel = hiltViewModel()
    val uiState = viewModel.uiState.collectAsState()

    when (uiState.value) {
        is DrinksState.Loaded -> {
            /* TODO Define Content*/
        }
        DrinksState.Loading -> CircularLoading(Modifier.fillMaxSize())
        DrinksState.Error -> ErrorMessage(Modifier.fillMaxSize())
    }
}

@Composable
fun Header(text: String) {
    Text(
        modifier = Modifier.padding(20.dp),
        text = text,
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold,
    )
}

@Composable
fun DrinkItem(
    modifier: Modifier = Modifier,
    drink: Drink,
    onDrinkSelected: (Drink) -> Unit,
) {
    Column(
        modifier = modifier
            .size(150.dp, 190.dp)
            .clickable { onDrinkSelected(drink) }
    ) {
        DrinkImage(drink)
        DrinkName(drink)
    }
}

@Composable
fun DrinkName(
    drink: Drink,
    modifier: Modifier = Modifier,
) {
    Text(
        text = drink.name,
        textAlign = TextAlign.Center,
        maxLines = 2,
        modifier = modifier
            .padding(8.dp)
    )
}

@Composable
fun DrinkImage(
    drink: Drink,
    modifier: Modifier = Modifier,
) {
    Image(
        modifier = modifier
            .aspectRatio(1f)
            .clip(RoundedCornerShape(32.dp))
            .border(
                BorderStroke(2.dp, DrinkTheme.colors.onSecondary),
                RoundedCornerShape(32.dp)
            ),
        painter = rememberImagePainter(data = drink.imageUrl),
        contentDescription = drink.name
    )
}

@Composable
fun CategoryChip(
    category: DrinkCategory,
    isSelected: Boolean,
    onCategorySelected: (DrinkCategory) -> Unit
) {
    Surface(
        elevation = 8.dp,
        shape = RoundedCornerShape(16.dp),
        border = if (isSelected) BorderStroke(
            width = 1.dp,
            color = DrinkTheme.colors.secondary
        ) else null,
        modifier = Modifier.clickable {
            onCategorySelected(category)
        },
    ) {
        Text(
            modifier = Modifier.padding(8.dp),
            text = category.categoryName
        )
    }
}

@Preview
@Composable
fun HeaderPreview() {
    Header("Test Header")
}

@Preview
@Composable
fun CategoryChipPreview() {
    CategoryChip(
        category = DrinkCategory.COCKTAIL,
        isSelected = true,
        onCategorySelected = {}
    )
}

@Preview
@Composable
fun DrinkItemPreview() {
    DrinkItem(
        drink = Drink(name = "Drink Name"),
        onDrinkSelected = { }
    )
}
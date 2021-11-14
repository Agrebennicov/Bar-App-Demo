package com.example.jetpackdemo.ui.dashboard

import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberImagePainter
import com.example.jetpackdemo.pojo.Drink
import com.example.jetpackdemo.pojo.DrinkCategory
import com.example.jetpackdemo.ui.dashboard.DashboardViewModel.DashboardState
import com.example.jetpackdemo.ui.theme.DrinkTheme

@Composable
fun DashboardScreen(
    navigateDrinkDetails: (id: Long) -> Unit
) {
    val viewModel: DashboardViewModel = hiltViewModel()
    val uiState = viewModel.uiState.collectAsState()

    when (uiState.value) {

        is DashboardState.Loaded -> {
            DashboardContent(
                state = uiState.value as DashboardState.Loaded,
                onCategorySelected = { viewModel.onCategoryChanged(it) },
                onDrinkSelected = { navigateDrinkDetails(it.id) }
            )
        }

        DashboardState.Loading -> {
            CircularLoading(Modifier.fillMaxSize())
        }
    }

}

@Composable
fun DashboardContent(
    state: DashboardState.Loaded,
    onCategorySelected: (DrinkCategory) -> Unit,
    onDrinkSelected: (Drink) -> Unit
) {
    val scrollableState = rememberScrollState()

    Column(Modifier.verticalScroll(scrollableState)) {

        CocktailsByCategories(
            categories = state.categories,
            drinksByCategory = state.drinksByCategory,
            selectedCategory = state.selectedCategory,
            onCategorySelected = onCategorySelected,
            onDrinkSelected = onDrinkSelected
        )

        state.randomDrink?.let {
            RandomCocktail(
                drink = state.randomDrink,
                onDrinkSelected = onDrinkSelected
            )
        }

        MostPopularCocktails(
            popularDrinks = state.popularDrinks,
            onDrinkSelected = onDrinkSelected
        )
    }
}

@Composable
fun CircularLoading(modifier: Modifier) {
    Box(modifier = modifier) {
        CircularProgressIndicator(
            modifier = Modifier.align(alignment = Alignment.Center),
            strokeWidth = 2.dp
        )
    }
}

@Composable
fun CocktailsByCategories(
    categories: List<DrinkCategory>,
    drinksByCategory: List<Drink>,
    selectedCategory: DrinkCategory,
    onCategorySelected: (DrinkCategory) -> Unit,
    onDrinkSelected: (Drink) -> Unit
) {

    Column {

        //Header
        DashboardHeader(
            text = "Browse by Categories"
        )

        //Chips
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(20.dp)
        ) {
            items(categories) { category ->
                CategoryChip(
                    category = category,
                    isSelected = category == selectedCategory,
                    onCategorySelected = onCategorySelected
                )
            }
        }

        //Drinks
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(20.dp)
        ) {
            items(drinksByCategory) {
                Column {
                    DrinkImage(
                        modifier = Modifier.width(150.dp),
                        drink = it,
                        onDrinkSelected = onDrinkSelected
                    )
                    Text(
                        text = it.name,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(vertical = 20.dp)
                    )
                }
            }
        }

    }
}

@Composable
fun RandomCocktail(
    drink: Drink,
    onDrinkSelected: (Drink) -> Unit
) {
    Column {
        DashboardHeader(text = "Random Cocktail")

        Row(
            modifier = Modifier.padding(20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            DrinkImage(
                modifier = Modifier.weight(0.5f),
                drink = drink,
                onDrinkSelected = onDrinkSelected
            )
            Text(
                modifier = Modifier
                    .weight(0.5f)
                    .padding(start = 20.dp),
                text = drink.name,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun MostPopularCocktails(
    popularDrinks: List<Drink>,
    onDrinkSelected: (Drink) -> Unit
) {
    Column {

        //Header
        DashboardHeader(
            text = "Most Popular"
        )

        //Drinks
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(20.dp)
        ) {
            items(popularDrinks) {
                Column {
                    DrinkImage(
                        modifier = Modifier.width(150.dp),
                        drink = it,
                        onDrinkSelected = onDrinkSelected
                    )
                    Text(
                        text = it.name,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(vertical = 20.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun DashboardHeader(text: String) {
    Text(
        modifier = Modifier.padding(20.dp),
        text = text,
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold,
    )
}

@Composable
fun DrinkImage(modifier: Modifier, drink: Drink, onDrinkSelected: (Drink) -> Unit) {

    Image(
        modifier = modifier
            .aspectRatio(1f)
            .clickable { onDrinkSelected(drink) }
            .clip(RoundedCornerShape(32.dp))
            .size(150.dp)
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
        modifier = Modifier.clickable {
            onCategorySelected(category)
        },
        elevation = 8.dp,
        shape = RoundedCornerShape(16.dp),
        border = if (isSelected) BorderStroke(
            width = 1.dp,
            color = DrinkTheme.colors.secondary
        ) else null,
    ) {
        Text(
            modifier = Modifier.padding(8.dp),
            text = category.categoryName
        )
    }
}
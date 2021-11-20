package com.example.jetpackdemo.ui.drinks

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberImagePainter
import com.example.jetpackdemo.pojo.Drink
import com.example.jetpackdemo.pojo.DrinkCategory
import com.example.jetpackdemo.ui.common.CircularLoading
import com.example.jetpackdemo.ui.common.ErrorMessage
import com.example.jetpackdemo.ui.drinks.DrinksViewModel.DrinksState
import com.example.jetpackdemo.ui.theme.DrinkTheme
import kotlinx.coroutines.launch

@Composable
fun DrinksScreen(
    navigateDrinkDetails: (id: Long) -> Unit
) {
    val viewModel: DrinksViewModel = hiltViewModel()
    val uiState = viewModel.uiState.collectAsState()

    when (uiState.value) {

        is DrinksState.Loaded -> {
            DrinksContent(
                state = uiState.value as DrinksState.Loaded,
                onCategorySelected = { viewModel.onCategoryChanged(it) },
                onDrinkSelected = { navigateDrinkDetails(it.id) }
            )
        }
        DrinksState.Loading -> CircularLoading(Modifier.fillMaxSize())
        DrinksState.Error -> ErrorMessage(Modifier.fillMaxSize())
    }
}

@Composable
fun DrinksContent(
    state: DrinksState.Loaded,
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

        Spacer(modifier = Modifier.height(10.dp))

        RandomCocktail(
            drink = state.randomDrink,
            onDrinkSelected = onDrinkSelected
        )

        Spacer(modifier = Modifier.height(10.dp))

        MostPopularCocktails(
            popularDrinks = state.popularDrinks,
            onDrinkSelected = onDrinkSelected
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
        val drinksListState = rememberLazyListState()
        val scope = rememberCoroutineScope()

        Header(text = "Browse by Categories")

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
                    onCategorySelected = {
                        onCategorySelected(it)
                        scope.launch {
                            drinksListState.animateScrollToItem(0)
                        }
                    }
                )
            }
        }

        //Drinks
        LazyRow(
            state = drinksListState,
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(20.dp),
        ) {
            items(drinksByCategory) {
                DrinkItem(
                    drink = it,
                    onDrinkSelected = onDrinkSelected
                )
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
        Header(text = "Random Cocktail")

        Row(
            modifier = Modifier.padding(20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            DrinkItem(
                modifier = Modifier.weight(0.5f),
                drink = drink,
                onDrinkSelected = onDrinkSelected,
                isHorizontalAlignment = true
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
        Header(text = "Most Popular")

        //Drinks
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(20.dp)
        ) {
            items(popularDrinks) {
                DrinkItem(
                    drink = it,
                    onDrinkSelected = onDrinkSelected
                )
            }
        }
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
    isHorizontalAlignment: Boolean = false
) {
    ConstraintLayout(
        modifier = modifier
            .size(150.dp, 190.dp)
            .clickable { onDrinkSelected(drink) }
    ) {
        val (image, text) = createRefs()

        Image(
            modifier = Modifier
                .constrainAs(image) {
                    if (isHorizontalAlignment) {
                        start.linkTo(parent.start)
                        end.linkTo(text.start)
                        centerVerticallyTo(parent)
                        width = Dimension.fillToConstraints
                    } else {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                    }
                }
                .aspectRatio(1f)
                .clip(RoundedCornerShape(32.dp))
                .border(
                    BorderStroke(2.dp, DrinkTheme.colors.onSecondary),
                    RoundedCornerShape(32.dp)
                ),
            painter = rememberImagePainter(data = drink.imageUrl),
            contentDescription = drink.name
        )

        Text(
            text = drink.name,
            textAlign = TextAlign.Center,
            maxLines = 2,
            modifier = Modifier
                .padding(8.dp)
                .constrainAs(text) {
                    if (isHorizontalAlignment) {
                        start.linkTo(image.end)
                        end.linkTo(parent.end)
                        centerVerticallyTo(parent)
                    } else {
                        top.linkTo(image.bottom, 16.dp)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        bottom.linkTo(parent.bottom)
                    }
                    width = Dimension.fillToConstraints
                }
        )

    }
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

@Preview
@Composable
fun DrinkItemHorizontalPreview() {
    DrinkItem(
        drink = Drink(name = "Drink Name"),
        onDrinkSelected = { },
        isHorizontalAlignment = true
    )
}
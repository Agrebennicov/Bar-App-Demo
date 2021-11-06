package com.example.jetpackdemo.ui.search

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberImagePainter
import coil.transform.CircleCropTransformation
import com.example.jetpackdemo.ui.theme.DrinkTheme

@Composable
fun SearchScreen(onDrinkClicked: (Long) -> Unit, searchValue: String) {
    val viewModel: SearchViewModel = hiltViewModel()

    viewModel.search(searchValue)

    val uiState by viewModel.uiState.collectAsState()
    Column(
        modifier = Modifier
            .background(DrinkTheme.colors.primary)
            .fillMaxSize()
    ) {


        Card(
            modifier = Modifier
                .fillMaxSize(),
            shape = RoundedCornerShape(topStart = 50.dp, topEnd = 50.dp)
        ) {
            when (uiState) {
                is SearchViewModel.SearchState.Empty -> {
                }
                is SearchViewModel.SearchState.Loaded -> {
                    val drinks = (uiState as SearchViewModel.SearchState.Loaded).drinks

                    LazyColumn(
                        contentPadding = PaddingValues(
                            horizontal = 16.dp,
                            vertical = 26.dp
                        ),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(drinks) { drink ->
                            Column(Modifier.clickable {
                                onDrinkClicked(drink.id)
                            }) {
                                Row {
                                    Image(
                                        modifier = Modifier
                                            .size(50.dp)
                                            .border(
                                                BorderStroke(
                                                    1.dp,
                                                    DrinkTheme.colors.onBackground
                                                ), CircleShape
                                            ),
                                        painter = rememberImagePainter(
                                            data = drink.imageUrl,
                                            builder = {
                                                transformations(
                                                    CircleCropTransformation()
                                                )
                                            }),
                                        contentDescription = drink.name
                                    )
                                    Column(modifier = Modifier.padding(start = 8.dp)) {
                                        Text(text = drink.name, fontSize = 18.sp)
                                        Spacer(modifier = Modifier.padding(top = 6.dp))
                                        Text(
                                            text = drink.category?.categoryName ?: "",
                                            fontSize = 12.sp
                                        )
                                    }
                                }
                                Spacer(Modifier.padding(top = 8.dp))
                                Divider(color = DrinkTheme.colors.onSurface)
                            }
                        }
                    }
                }
            }
        }
    }
}


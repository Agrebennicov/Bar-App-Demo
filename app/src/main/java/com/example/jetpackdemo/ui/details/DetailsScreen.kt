package com.example.jetpackdemo.ui.details

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberImagePainter
import com.example.jetpackdemo.ui.theme.DrinkTheme

@Composable
fun DetailsScreen(id: Long) {
    val viewModel: DetailsViewModel = hiltViewModel()
    LaunchedEffect(key1 = true) {
        viewModel.getDetails(id)
    }

    val uiState by viewModel.uiState.collectAsState()
    Column(
        modifier = Modifier
            .background(DrinkTheme.colors.primary)
            .fillMaxSize()
    ) {
        Card(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            shape = RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp)
        ) {
            when (uiState) {
                is DetailsViewModel.DetailsState.Loading -> {
                    Column(
                        Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        CircularProgressIndicator()
                    }
                }
                is DetailsViewModel.DetailsState.Loaded -> {
                    val drink = (uiState as DetailsViewModel.DetailsState.Loaded).drink
                    Column(
                        Modifier
                            .fillMaxWidth()
                    ) {
                        Image(
                            modifier = Modifier
                                .fillMaxWidth()
                                .aspectRatio(1f),
                            contentScale = ContentScale.FillWidth,
                            painter = rememberImagePainter(data = drink.imageUrl),
                            contentDescription = drink.name
                        )

                        Column(
                            Modifier
                                .fillMaxWidth()
                                .padding(vertical = 16.dp, horizontal = 16.dp)
                        ) {
                            Row(
                                Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceEvenly,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                    Text(
                                        text = "Category",
                                        textAlign = TextAlign.Center,
                                        fontSize = 18.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                    Spacer(modifier = Modifier.height(10.dp))
                                    Text(
                                        text = drink.category?.categoryName ?: "",
                                        textAlign = TextAlign.Center,
                                        fontSize = 12.sp,
                                    )
                                }
                                Divider(Modifier.height(30.dp).width(1.dp), color = DrinkTheme.colors.onBackground)
                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                    Text(
                                        text = "Glass",
                                        textAlign = TextAlign.Center,
                                        fontSize = 18.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                    Spacer(modifier = Modifier.height(10.dp))
                                    Text(
                                        text = drink.glass ?: "", textAlign = TextAlign.Center,
                                        fontSize = 12.sp,
                                    )
                                }
                                Divider(Modifier.height(30.dp).width(1.dp), color = DrinkTheme.colors.onBackground)
                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                    Text(
                                        text = "Alcoholic",
                                        textAlign = TextAlign.Center,
                                        fontSize = 18.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                    Spacer(modifier = Modifier.height(10.dp))
                                    Text(
                                        text = drink.alcoholicType?.typeName ?: "",
                                        textAlign = TextAlign.Center,
                                        fontSize = 12.sp,
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.height(10.dp))
                            Divider(color = DrinkTheme.colors.onSurface)
                            Spacer(modifier = Modifier.height(10.dp))
                            Text(
                                text = "Ingredients:",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold
                            )

                            Spacer(modifier = Modifier.height(10.dp))
                            drink.ingredient1?.let { Text(text = drink.ingredient1) }
                            drink.ingredient2?.let { Text(text = drink.ingredient2) }
                            drink.ingredient3?.let { Text(text = drink.ingredient3) }
                            drink.ingredient4?.let { Text(text = drink.ingredient4) }
                            drink.ingredient5?.let { Text(text = drink.ingredient5) }
                            drink.ingredient6?.let { Text(text = drink.ingredient6) }
                            drink.ingredient7?.let { Text(text = drink.ingredient7) }
                            drink.ingredient8?.let { Text(text = drink.ingredient8) }



                            Spacer(modifier = Modifier.height(10.dp))
                            Divider(color = DrinkTheme.colors.onSurface)

                            Spacer(modifier = Modifier.height(10.dp))
                            Text(
                                text = "Instructions:",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.height(10.dp))
                            Text(text = drink.instructions ?: "")
                        }
                    }
                }
            }
        }
    }
}
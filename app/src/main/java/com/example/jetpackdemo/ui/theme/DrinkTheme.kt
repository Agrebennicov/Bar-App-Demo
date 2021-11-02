package com.example.jetpackdemo.ui.theme

import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

@Composable
fun DrinkTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colors = colorPalette,
        content = content
    )
}

object DrinkTheme {
    val colors: Colors
        @Composable
        get() = colorPalette
}

val colorPalette = lightColors(
    primary = Color(0xFF1F1F1F),
    primaryVariant = Color(0xFF3E2723),
    secondary = Color(0xFFFF5722),
    background = Color(0xFFFCFCFC),
    surface = DrinkColors.Black,
    error = Color(0xFFCE1F3E),
    onPrimary = DrinkColors.White,
    onSecondary = DrinkColors.White,
    onBackground = DrinkColors.White,
    onSurface = DrinkColors.White,
    onError = DrinkColors.White
)

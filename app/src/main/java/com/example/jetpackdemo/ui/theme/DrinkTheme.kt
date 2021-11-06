package com.example.jetpackdemo.ui.theme

import android.annotation.SuppressLint
import androidx.compose.material.Colors
import androidx.compose.material.MaterialTheme
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
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

@SuppressLint("ConflictingOnColor")
val colorPalette = lightColors(
    primary = Color(0xFFFFEDD3),
    primaryVariant = Color(0xFF3E2723),
    secondary = Color(0xFFFF5722),
    background = Color(0xFFE8F2FB),
    surface = Color(0xFFE8F2FB),
    error = Color(0xFFCE1F3E),
    onPrimary = Color(0xFFFF5722),
    onSecondary = DrinkColors.White,
    onBackground = Color(0xFF5DB0E6),
    onSurface = Color(0xFFE5E5E5),
    onError = DrinkColors.White
)

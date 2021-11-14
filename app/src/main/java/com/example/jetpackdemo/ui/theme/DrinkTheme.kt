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
    primary = Color(0xFFFFF0D7),
    primaryVariant = Color(0xFF3E2723),
    secondary = Color(0xFFE5863A),
    background = Color(0xFFE9E2D6),
    surface = Color(0xFFFFF0D7),
    error = Color(0xFFCE1F3E),
    onPrimary = Color(0xFFE5863A),
    onSecondary =  Color(0xFFE5863A),
    onBackground = Color(0xFFC05703),
    onSurface = Color(0xFFE5E5E5),
    onError = Color(0xffffffff)
)

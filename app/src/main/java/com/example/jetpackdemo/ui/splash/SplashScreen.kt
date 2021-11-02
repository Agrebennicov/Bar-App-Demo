package com.example.jetpackdemo.ui.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.jetpackdemo.R
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(onDone: () -> Unit) {
    BarCounter {
        LaunchedEffect(key1 = true ){
            delay(1000)
            onDone()
        }
    }
}

@Composable
fun BarCounter(
    drink: @Composable () -> Unit
) {
    Column(
        Modifier
            .background(Color(0xFFFCDEAE))
            .fillMaxSize()
    ) {

        Box(
            Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            drink()
        }

        Image(
            modifier = Modifier
                .height(200.dp)
                .fillMaxWidth()
                .background(Color.LightGray),
            painter = painterResource(R.drawable.barcounter),
            contentDescription = null,
            contentScale = ContentScale.FillHeight
        )

    }
}

@Preview
@Composable
fun test() {
    SplashScreen {}
}

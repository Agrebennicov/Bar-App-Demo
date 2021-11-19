package com.example.jetpackdemo.ui.common

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ErrorMessage(modifier: Modifier) {
    Box(modifier = modifier) {
        Text(
            text = "Ooops, something went wrong!",
            fontSize = 20.sp,
            modifier = Modifier
                .padding(20.dp)
                .align(Alignment.Center)
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
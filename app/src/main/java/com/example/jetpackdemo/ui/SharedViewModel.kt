package com.example.jetpackdemo.ui

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class SharedViewModel: ViewModel() {
    val drinkName = mutableStateOf("")
}
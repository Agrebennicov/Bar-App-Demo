package com.example.jetpackdemo.ui.splash

import androidx.lifecycle.ViewModel
import com.example.jetpackdemo.network.CocktailRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val cocktailRepository: CocktailRepository
) : ViewModel() {

}
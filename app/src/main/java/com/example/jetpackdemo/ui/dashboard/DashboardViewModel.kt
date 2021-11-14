package com.example.jetpackdemo.ui.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetpackdemo.network.CocktailRepository
import com.example.jetpackdemo.pojo.Drink
import com.example.jetpackdemo.pojo.DrinkCategory
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val cocktailRepository: CocktailRepository
) : ViewModel() {

    private val _uiState: MutableStateFlow<DashboardState> = MutableStateFlow(DashboardState.Loading)
     val uiState: StateFlow<DashboardState> = _uiState

    init {
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch {
            val categories = DrinkCategory.values().asList()
            val defaultCategory = categories.first()
            val drinksByCategories = cocktailRepository.getByCategory(defaultCategory)
            val randomDrink = cocktailRepository.getRandom()
            val popularDrinks = cocktailRepository.getPopular()

            _uiState.value = DashboardState.Loaded(
                categories = categories,
                selectedCategory = defaultCategory,
                drinksByCategory = drinksByCategories.getOrDefault(listOf()),
                randomDrink = randomDrink.getOrNull(),
                popularDrinks = popularDrinks.getOrDefault(listOf())
            )
        }
    }

    fun onCategoryChanged(category: DrinkCategory) {
        viewModelScope.launch {
            val drinksByCategories = cocktailRepository.getByCategory(category = category)

            (_uiState.value as? DashboardState.Loaded)?.let {
                _uiState.value = it.copy(
                    selectedCategory = category,
                    drinksByCategory = drinksByCategories.getOrDefault(
                        listOf()
                    )
                )
            }
        }
    }

    sealed class DashboardState {
        object Loading : DashboardState()
        data class Loaded(
            val categories: List<DrinkCategory>,
            val selectedCategory: DrinkCategory,
            val drinksByCategory: List<Drink>,
            val popularDrinks: List<Drink>,
            val randomDrink: Drink?
        ) : DashboardState()
    }
}
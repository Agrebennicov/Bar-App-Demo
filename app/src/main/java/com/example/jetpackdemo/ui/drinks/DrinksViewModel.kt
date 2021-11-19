package com.example.jetpackdemo.ui.drinks

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
class DrinksViewModel @Inject constructor(
    private val cocktailRepository: CocktailRepository
) : ViewModel() {

    private val _uiState: MutableStateFlow<DrinksState> = MutableStateFlow(DrinksState.Loading)
    val uiState: StateFlow<DrinksState> = _uiState

    init {
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch {
            val categories = DrinkCategory.values().asList()
            val defaultCategory = categories.first()
            val drinksByCategories = cocktailRepository.getByCategory(defaultCategory).getOrNull()
            val randomDrink = cocktailRepository.getRandom().getOrNull()
            val popularDrinks = cocktailRepository.getPopular().getOrNull()

            if (drinksByCategories != null && randomDrink != null && popularDrinks != null) {
                _uiState.value = DrinksState.Loaded(
                    categories = categories,
                    selectedCategory = defaultCategory,
                    drinksByCategory = drinksByCategories,
                    randomDrink = randomDrink,
                    popularDrinks = popularDrinks
                )
            } else {
                _uiState.value = DrinksState.Error
            }
        }
    }

    fun onCategoryChanged(category: DrinkCategory) {
        viewModelScope.launch {
            val drinksByCategories = cocktailRepository.getByCategory(category = category)

            (_uiState.value as? DrinksState.Loaded)?.let {
                _uiState.value = it.copy(
                    selectedCategory = category,
                    drinksByCategory = drinksByCategories.getOrDefault(
                        listOf()
                    )
                )
            }
        }
    }

    sealed class DrinksState {
        object Loading : DrinksState()
        object Error : DrinksState()
        data class Loaded(
            val categories: List<DrinkCategory>,
            val selectedCategory: DrinkCategory,
            val drinksByCategory: List<Drink>,
            val popularDrinks: List<Drink>,
            val randomDrink: Drink
        ) : DrinksState()
    }
}
package com.example.jetpackdemo.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetpackdemo.network.CocktailRepository
import com.example.jetpackdemo.pojo.Drink
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val cocktailRepository: CocktailRepository
) : ViewModel() {
    val uiState: MutableStateFlow<SearchState> = MutableStateFlow(SearchState.Empty)

    fun search(searchValue: String) {
        viewModelScope.launch {
            cocktailRepository.search(searchValue)
                .onSuccess {
                    uiState.value = SearchState.Loaded(it)
                }
                .onFailure {
                    uiState.value = SearchState.Empty
                }
        }
    }

    sealed class SearchState {
        object Empty : SearchState()
        data class Loaded(val drinks: List<Drink>) : SearchState()
    }
}
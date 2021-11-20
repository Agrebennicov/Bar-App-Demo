package com.example.jetpackdemo.ui.details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetpackdemo.network.CocktailRepository
import com.example.jetpackdemo.pojo.Drink
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val cocktailRepository: CocktailRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    val uiState: MutableStateFlow<DetailsState> = MutableStateFlow(DetailsState.Loading)

    init {
        getDetails(savedStateHandle["id"] ?: 1)
    }

    private fun getDetails(id: Long) {
        viewModelScope.launch {
            cocktailRepository.getDetails(id)
                .onSuccess {
                    uiState.value = DetailsState.Loaded(it)
                }
                .onFailure {
                    uiState.value = DetailsState.Loading
                }
        }
    }

    sealed class DetailsState {
        object Loading : DetailsState()
        data class Loaded(val drink: Drink) : DetailsState()
    }
}
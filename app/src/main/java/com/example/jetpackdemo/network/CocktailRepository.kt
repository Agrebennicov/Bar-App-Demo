package com.example.jetpackdemo.network

import com.example.jetpackdemo.pojo.Drink
import com.example.jetpackdemo.pojo.DrinkCategory
import javax.inject.Inject

class CocktailRepository @Inject constructor(private val cocktailService: CocktailService) {

    suspend fun search(query: String): Result<List<Drink>> = kotlin.runCatching {
        val result = cocktailService.search(query)
        return if (!result.drinks.isNullOrEmpty()) {
            Result.success(result.drinks)
        } else {
            Result.success(emptyList())
        }
    }

    suspend fun getRandom(): Result<Drink> = kotlin.runCatching {
        val result = cocktailService.getRandom().drinks?.firstOrNull()
        return if (result != null) {
            Result.success(result)
        } else {
            Result.failure(IllegalStateException("Random cocktail is null"))
        }
    }

    suspend fun getPopular(): Result<List<Drink>> = kotlin.runCatching {
        val result = cocktailService.getPopular()
        return if (!result.drinks.isNullOrEmpty()) {
            Result.success(result.drinks)
        } else {
            Result.failure(IllegalStateException("There should be at least something.."))
        }
    }

    suspend fun getByCategory(category: DrinkCategory): Result<List<Drink>> = kotlin.runCatching {
        val result = cocktailService.getByCategory(category)
        return if (!result.drinks.isNullOrEmpty()) {
            Result.success(result.drinks)
        } else {
            Result.failure(IllegalStateException("No cocktails in this category o_0"))
        }
    }

    suspend fun getDetails(id: Long): Result<Drink> = kotlin.runCatching {
        val result = cocktailService.getDetails(id).drinks?.firstOrNull()
        return if (result != null) {
            Result.success(result)
        } else {
            Result.failure(IllegalStateException("No cocktail with such ID: $id"))
        }
    }
}

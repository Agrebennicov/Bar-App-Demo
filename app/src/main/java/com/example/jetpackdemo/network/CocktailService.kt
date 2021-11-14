package com.example.jetpackdemo.network

import com.example.jetpackdemo.pojo.DrinkCategory
import com.example.jetpackdemo.pojo.DrinkResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CocktailService {

    @GET("search.php")
    suspend fun search(@Query("s") searchQuery: String): Response<DrinkResponse>

    @GET("randomselection.php")
    suspend fun getRandom(): DrinkResponse

    @GET("popular.php")
    suspend fun getPopular(): DrinkResponse

    @GET("filter.php")
    suspend fun getByCategory(@Query("c") category: String): DrinkResponse

    @GET("lookup.php")
    suspend fun getDetails(@Query("i") id: Long): DrinkResponse
}

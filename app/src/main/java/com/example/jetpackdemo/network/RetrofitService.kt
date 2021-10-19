package com.example.jetpackdemo.network

import okhttp3.OkHttpClient
import retrofit2.Retrofit

class RetrofitService {
    fun createService(okHttpClient: OkHttpClient): CocktailService = Retrofit.Builder()
        .client(okHttpClient)
        .baseUrl("$BASE_URL/$API_KEY/")
        .build().create(CocktailService::class.java)

    companion object {
        private const val BASE_URL = "https://www.thecocktaildb.com/api/json/v2"
        private const val API_KEY = "9973533"
    }
}

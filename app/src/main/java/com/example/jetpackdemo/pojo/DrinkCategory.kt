package com.example.jetpackdemo.pojo

import com.google.gson.annotations.SerializedName

enum class DrinkCategory {
    @SerializedName("Cocktail")
    COCKTAIL,

    @SerializedName("Shot")
    SHOT,

    @SerializedName("Homemade Liqueur")
    HOMEMADE_LIQUEUR,

    @SerializedName("Punch / Party Drink")
    PARTY_DRINK,

    @SerializedName("Ordinary Drink")
    ORDINARY_DRINK,

    @SerializedName("BEER")
    BEER,

    @SerializedName("Milk / Float / Shake")
    MILK_SHAKE,

    @SerializedName("Cocoa")
    COCOA,

    @SerializedName("Coffee / Tea")
    COFFEE_TEA,

    @SerializedName("Soft Drink / Soda")
    SOFT_DRINK,

    @SerializedName("Other/Unknown")
    OTHER;
}
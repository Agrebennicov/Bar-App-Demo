package com.example.jetpackdemo.pojo

import com.google.gson.annotations.SerializedName

enum class DrinkCategory(val categoryName: String) {
    @SerializedName("Cocktail")
    COCKTAIL("Cocktail"),

    @SerializedName("Shot")
    SHOT("Shot"),

    @SerializedName("Homemade Liqueur")
    HOMEMADE_LIQUEUR("Homemade Liqueur"),

    @SerializedName("Punch / Party Drink")
    PARTY_DRINK("Punch / Party Drink"),

    @SerializedName("Ordinary Drink")
    ORDINARY_DRINK("Ordinary Drink"),

    @SerializedName("BEER")
    BEER("BEER"),

    @SerializedName("Milk / Float / Shake")
    MILK_SHAKE("Milk / Float / Shake"),

    @SerializedName("Cocoa")
    COCOA("Cocoa"),

    @SerializedName("Coffee / Tea")
    COFFEE_TEA("Coffee / Tea"),

    @SerializedName("Soft Drink / Soda")
    SOFT_DRINK("Soft Drink / Soda"),

    @SerializedName("Other/Unknown")
    OTHER("Other/Unknown");
}
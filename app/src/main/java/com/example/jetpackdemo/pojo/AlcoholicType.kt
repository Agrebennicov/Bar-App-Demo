package com.example.jetpackdemo.pojo

import com.google.gson.annotations.SerializedName

enum class AlcoholicType {
    @SerializedName("Non alcoholic")
    NON_ALCOHOLIC,

    @SerializedName("Alcoholic")
    ALCOHOLIC();
}
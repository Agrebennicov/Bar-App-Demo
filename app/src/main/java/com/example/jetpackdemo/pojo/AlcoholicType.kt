package com.example.jetpackdemo.pojo

import com.google.gson.annotations.SerializedName

enum class AlcoholicType(val typeName: String) {
    @SerializedName("Non alcoholic")
    NON_ALCOHOLIC("Non alcoholic"),

    @SerializedName("Alcoholic")
    ALCOHOLIC("Alcoholic"),

    @SerializedName("Optional alcohol")
    OPTIONAL_ALCOHOL("Optional alcohol");
}
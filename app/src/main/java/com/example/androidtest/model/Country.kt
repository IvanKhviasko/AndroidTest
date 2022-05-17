package com.example.androidtest.model

import com.google.gson.annotations.SerializedName

data class Country(
    val name: String,
    @SerializedName("flag")
    val flag: String
)
package com.example.androidtest.model

import com.google.gson.annotations.SerializedName

data class CountryDetails(
    val countries: List<Countries>
)


data class Countries(
    val name: String,
    @SerializedName("flag")
    val flag: String,
    val area: String,
    val population: String
)

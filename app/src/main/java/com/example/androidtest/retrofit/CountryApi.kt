package com.example.androidtest.retrofit

import com.example.androidtest.model.Countries
import com.example.androidtest.model.Country
import com.example.androidtest.model.CountryDetails
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path


interface CountryApi {

    @GET("all")
    fun getCountry(
    ): Call<List<Country>>

    @GET("name/{name}")
    fun getCountryDetails(
        @Path("name") name: String
    ): Call<CountryDetails>
}
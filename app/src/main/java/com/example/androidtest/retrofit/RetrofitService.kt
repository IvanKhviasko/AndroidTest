package com.example.androidtest.retrofit

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

object RetrofitService {

    private val retrofit by lazy(LazyThreadSafetyMode.NONE) { provideRetrofit() }
    val countryApi by lazy(LazyThreadSafetyMode.NONE) {
        retrofit.create<CountryApi>()
    }

    private fun provideRetrofit(): Retrofit {
        val client = OkHttpClient.Builder()
            .build()

        return Retrofit.Builder()
            .baseUrl("https://countriesinfo21.herokuapp.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }
}
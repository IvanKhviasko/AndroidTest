package com.example.androidtest

import android.content.Context
import androidx.room.Room
import com.example.androidtest.room.AppDataBase
import com.example.androidtest.repositary.CountryRepository

object Repositories {

    private lateinit var applicationContext: Context

    private val dataBase: AppDataBase by lazy {
        Room.databaseBuilder(applicationContext, AppDataBase::class.java, "database-room")
            .allowMainThreadQueries()
            .build()
    }

    val COUNTRY_REPOSITORY: CountryRepository by lazy {
        CountryRepository(dataBase.countryDao())
    }

    fun init(context: Context) {
        applicationContext = context
    }
}
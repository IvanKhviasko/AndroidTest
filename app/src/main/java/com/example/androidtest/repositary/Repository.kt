package com.example.androidtest.repositary

import com.example.androidtest.model.Country

interface Repository {

    fun getAll(): List<Country>

    fun deleteCountry(country: Country)

    fun insertCountry(country: Country)

    fun updateCountry(oldCountry: Country, newCountry: Country)
}
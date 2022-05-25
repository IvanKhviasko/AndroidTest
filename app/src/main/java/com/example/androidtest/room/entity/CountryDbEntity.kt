package com.example.androidtest.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.androidtest.model.Country

@Entity(tableName = "country")
data class CountryDbEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    @ColumnInfo(name = "country_name")
    var countryName: String,
    @ColumnInfo(name = "capital_name")
    var capitalName: String,
) {

    fun toCountry(): Country = Country(
        id = id,
        countryName = countryName,
        capitalName = capitalName,
    )
}
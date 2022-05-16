package com.example.androidtest.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.androidtest.room.entity.CountryDbEntity

@Database(entities = [CountryDbEntity::class], version = 1)
abstract class AppDataBase : RoomDatabase() {

    abstract fun countryDao(): CountryDao
}
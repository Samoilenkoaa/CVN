package com.bignerdranch.android.cvn.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.bignerdranch.android.cvn.data.CountryCovidInfo

@Database(entities = [CountryCovidInfo::class], version = 1)
abstract class Database : RoomDatabase(){
    abstract fun getCountriesDao(): CountriesDao
}
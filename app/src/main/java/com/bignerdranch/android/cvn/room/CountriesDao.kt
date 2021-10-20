package com.bignerdranch.android.cvn.room

import androidx.room.*
import com.bignerdranch.android.cvn.data.CountryCovidInfo

@Dao
interface CountriesDao {

    @Query("SELECT * FROM countries")
    suspend fun getAllCountries(): List<CountryCovidInfo>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertCountry(country: CountryCovidInfo)

    @Delete
    suspend fun deleteCountry(country: CountryCovidInfo)
}
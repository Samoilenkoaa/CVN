package com.bignerdranch.android.cvn.di

import android.content.Context
import androidx.room.Room
import com.bignerdranch.android.cvn.room.CountriesDao
import com.bignerdranch.android.cvn.room.Database
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object FavoriteModule {

    @Singleton
    @Provides
    fun provideCountriesDao(@ApplicationContext context: Context): CountriesDao {
        val database = Room.databaseBuilder(context, Database::class.java, "db").build()
        val countriesDao = database.getCountriesDao()
        return countriesDao
    }
}
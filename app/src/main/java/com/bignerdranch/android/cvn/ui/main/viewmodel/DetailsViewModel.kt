package com.bignerdranch.android.cvn.ui.main.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bignerdranch.android.cvn.data.CountryCovidInfo
import com.bignerdranch.android.cvn.room.CountriesDao
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(val countriesDao: CountriesDao) : ViewModel() {

    fun addToFavorite(country: CountryCovidInfo) {
        viewModelScope.launch {
            countriesDao.insertCountry(country)
        }
    }
}
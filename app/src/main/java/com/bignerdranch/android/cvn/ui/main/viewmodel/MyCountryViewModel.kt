package com.bignerdranch.android.cvn.ui.main.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bignerdranch.android.cvn.data.CountryCovidInfo
import com.bignerdranch.android.cvn.remotedatasource.RemoteDataSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class MyCountryViewModel @Inject constructor(val remoteDataSource: RemoteDataSource) : ViewModel() {
    data class MyCountryHistoryInfo(
        val today: CountryCovidInfo,
        val yesterday: CountryCovidInfo,
        val twoDaysAgo: CountryCovidInfo
    )
    val countriesForSpinnerLiveData = MutableLiveData<List<CountryCovidInfo>>()
    val errorLiveData = MutableLiveData<Exception>()
    val historySelectedCountryLiveData = MutableLiveData<MyCountryHistoryInfo>()

    fun refreshCountriesForSpinner() {
        viewModelScope.launch(Dispatchers.Main) {
            val countries = remoteDataSource.getAllCountriesInfo()
            countriesForSpinnerLiveData.value = countries
        }
    }

    fun onSelectedCountryChange(country: CountryCovidInfo) {
        viewModelScope.launch {
            try {
                val today = remoteDataSource.getCountryInfo(country.name)
                val yesterday = remoteDataSource.yesterdayCountry(country.name)
                val twoDaysAgo = remoteDataSource.twoDaysAgoCountry(country.name)
                val history = MyCountryHistoryInfo(
                    today, yesterday, twoDaysAgo
                )
                historySelectedCountryLiveData.value = history
            } catch (e: Exception) {
                errorLiveData.value = e
            }
        }
    }
}
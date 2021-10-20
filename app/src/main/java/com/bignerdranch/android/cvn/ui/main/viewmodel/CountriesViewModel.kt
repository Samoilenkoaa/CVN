package com.bignerdranch.android.cvn.ui.main.viewmodel

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bignerdranch.android.cvn.LoadingResult
import com.bignerdranch.android.cvn.data.CountryCovidInfo
import com.bignerdranch.android.cvn.remotedatasource.RemoteDataSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class CountriesViewModel @Inject constructor(val remoteDataSource: RemoteDataSource) : ViewModel() {
    val countriesResponseLiveData = MutableLiveData<LoadingResult<List<CountryCovidInfo>>>()
    val filterLiveData = MutableLiveData<String>()
    val errorLiveData = MutableLiveData<Exception>()
    val sortedForwardTypeLiveData = MutableLiveData<Boolean>()
    val countriesLiveData = MediatorLiveData<List<CountryCovidInfo>>().apply {
        addSource(countriesResponseLiveData) { value = filterAndSortCountries() }
        addSource(filterLiveData) { value = filterAndSortCountries() }
        addSource(sortedForwardTypeLiveData) { value = filterAndSortCountries() }
    }
    val isLoadingLiveData = MutableLiveData<Boolean>(false)
    fun onRefreshButtonClick() {
        try {
            viewModelScope.launch(Dispatchers.Main) {
                isLoadingLiveData.value = true
                val countries = remoteDataSource.getAllCountriesInfoLoadingResult()
                countriesResponseLiveData.value = countries
                isLoadingLiveData.value = false
            }
        } catch (e: Exception) {
            errorLiveData.value = e
        }

    }

    fun filterAndSortCountries(): List<CountryCovidInfo>? {
        val loadingResult = countriesResponseLiveData.value
        if (loadingResult is LoadingResult.Success) {
            val filterText = filterLiveData.value ?: ""
            val filteredList = loadingResult.data.filter { it.name.startsWith(filterText, true) }
            val sortedCountries = if (sortedForwardTypeLiveData.value == true)
                filteredList.sortedBy { it.name }
            else
                filteredList.sortedByDescending { it.name }
            return sortedCountries
        }
        return null
    }
}
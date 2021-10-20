package com.bignerdranch.android.cvn.ui.main.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bignerdranch.android.cvn.data.CountryCovidInfo
import com.bignerdranch.android.cvn.remotedatasource.RemoteDataSource
import com.bignerdranch.android.cvn.room.CountriesDao
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    val remoteDataSource: RemoteDataSource,
    val countriesDao: CountriesDao
) : ViewModel() {

    val favoriteCountriesLiveData = MutableLiveData<List<CountryCovidInfo>>()
    val isLoadingLiveData = MutableLiveData<Boolean>(false)

    fun refreshCountries() {
        viewModelScope.launch {
            isLoadingLiveData.value = true
            val favoriteCountries = countriesDao.getAllCountries()
            val actualCountries = mutableListOf<CountryCovidInfo>()
            for (favoriteCountry in favoriteCountries) {
                val countryActualInfo = remoteDataSource.getCountryInfo(favoriteCountry.name)
                actualCountries.add(countryActualInfo)
            }
            isLoadingLiveData.value = false
            favoriteCountriesLiveData.value = actualCountries
        }
    }

    fun deleteFavorite(position: Int) {
        val item = favoriteCountriesLiveData.value?.get(position)
        item?.let {
            viewModelScope.launch {
                countriesDao.deleteCountry(it)
                refreshCountries()
            }
        }
    }
}
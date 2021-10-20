package com.bignerdranch.android.cvn.ui.main.adaptercountries

import com.bignerdranch.android.cvn.data.CountryCovidInfo

interface CountriesAdapterListener {
    fun onCountryItemClick(country: CountryCovidInfo)
}
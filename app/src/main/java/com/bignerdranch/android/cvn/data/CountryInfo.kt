package com.bignerdranch.android.cvn.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CountryInfo(
    var flag: String = ""
) : Parcelable
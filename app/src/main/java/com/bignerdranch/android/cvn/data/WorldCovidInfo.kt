package com.bignerdranch.android.cvn.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class WorldCovidInfo(
    val cases: Double,
    val deaths: Double,
    val recovered: Double

): Parcelable
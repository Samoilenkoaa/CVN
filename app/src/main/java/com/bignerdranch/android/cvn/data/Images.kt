package com.bignerdranch.android.cvn.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Images(
    val url: String
) : Parcelable
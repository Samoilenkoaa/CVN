package com.bignerdranch.android.cvn.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class News(
    val title: String,
    val webUrl: String,
    val images: List<Images>?,
    val content: String,
    val type: String,
    val publishedDateTime: String,
) : Parcelable
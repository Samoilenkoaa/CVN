package com.bignerdranch.android.cvn.data

import android.os.Parcelable
import androidx.annotation.Keep
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Entity(tableName = "countries")
@Keep
@Parcelize
data class CountryCovidInfo(
    @PrimaryKey
    @SerializedName("country")
    var name: String = "",
    @Ignore
    var cases: Long = 0L,
    @Ignore
    var deaths: Long = 0L,
    @Ignore
    var recovered: Long = 0L,
    @Ignore
    var countryInfo: CountryInfo? = null
) : Parcelable
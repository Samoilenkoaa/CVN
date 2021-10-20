package com.bignerdranch.android.cvn.remotedatasource

import com.bignerdranch.android.cvn.LoadingResult
import com.bignerdranch.android.cvn.data.CountryCovidInfo
import com.bignerdranch.android.cvn.data.WorldCovidInfo
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteDataSource @Inject constructor() {

    companion object {
        private const val BASE_URL = "https://disease.sh"
    }

    private interface API {
        @GET("/v3/covid-19/countries")
        suspend fun getAllCountriesInfo(): List<CountryCovidInfo>

        @GET("/v3/covid-19/countries/{country}")
        suspend fun getCountryInfo(@Path("country") countryName: String): CountryCovidInfo

        @GET("/v3/covid-19/all")
        suspend fun getWorldInfo(): WorldCovidInfo

        @GET("/v3/covid-19/all?twoDaysAgo=true")
        suspend fun twoDaysAgoWorldInfo(): WorldCovidInfo

        @GET("/v3/covid-19/all?yesterday=true")
        suspend fun yesterdayWorldInfo(): WorldCovidInfo

        @GET("/v3/covid-19/countries/{country}?twoDaysAgo=true")
        suspend fun twoDaysAgoCountry(@Path("country") countryName: String): CountryCovidInfo

        @GET("/v3/covid-19/countries/{country}?yesterday=true")
        suspend fun yesterdayCountry(@Path("country") countryName: String): CountryCovidInfo

    }

    private var retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()


    private var retrofitService = retrofit.create(API::class.java)  // есть вопросик???

    suspend fun getAllCountriesInfo(): List<CountryCovidInfo> {
        return retrofitService.getAllCountriesInfo()

    }

    suspend fun getAllCountriesInfoLoadingResult(): LoadingResult<List<CountryCovidInfo>> {
        try {
            val result = retrofitService.getAllCountriesInfo()
            return LoadingResult.Success(result)
        } catch (e: Exception) {
            return LoadingResult.Error(e)
        }
    }

    suspend fun getWorldInfo(): WorldCovidInfo {
        return retrofitService.getWorldInfo()
    }

    suspend fun getCountryInfo(countryName: String): CountryCovidInfo {
        return retrofitService.getCountryInfo(countryName)
    }

    suspend fun twoDaysAgoWorldInfo(): WorldCovidInfo {
        return retrofitService.twoDaysAgoWorldInfo()
    }

    suspend fun yesterdayWorldInfo(): WorldCovidInfo {
        return retrofitService.yesterdayWorldInfo()
    }


    suspend fun twoDaysAgoCountry(countryName: String): CountryCovidInfo {
        return retrofitService.twoDaysAgoCountry(countryName)
    }

    suspend fun yesterdayCountry(countryName: String): CountryCovidInfo {
        return retrofitService.yesterdayCountry(countryName)
    }
}
package com.bignerdranch.android.cvn.remotedatasource


import com.bignerdranch.android.cvn.BuildConfig
import com.bignerdranch.android.cvn.LoadingResult
import com.bignerdranch.android.cvn.data.News
import com.bignerdranch.android.cvn.data.NewsResponce
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class RemoteDataSourceNews @Inject constructor() {

    companion object {
        private const val BASE_URL = "https://coronavirus-smartable.p.rapidapi.com/"
    }

    private interface API {

        @Headers(
            "x-rapidapi-host: coronavirus-smartable.p.rapidapi.com",
            "x-rapidapi-key: ${BuildConfig.NEW_COVID_API_KEY}"
        )
        @GET("news/v1/global/")
        suspend fun getAllNews(): NewsResponce
    }

    private var retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private var retrofitService = retrofit.create(API::class.java)
    suspend fun getAllNewsInfo(): LoadingResult<List<News>> {
        try {
            val result = retrofitService.getAllNews()
            return LoadingResult.Success(result.news)
        } catch (e: Exception) {
            return LoadingResult.Error(e)
        }
    }
}
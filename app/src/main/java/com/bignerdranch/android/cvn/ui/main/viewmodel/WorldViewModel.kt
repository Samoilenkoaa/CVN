package com.bignerdranch.android.cvn.ui.main.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bignerdranch.android.cvn.data.WorldCovidInfo
import com.bignerdranch.android.cvn.remotedatasource.RemoteDataSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject


data class WorldHistoryInfo(
    val today: WorldCovidInfo,
    val yesterday: WorldCovidInfo,
    val twoDaysAgo: WorldCovidInfo
)

@HiltViewModel
class WorldViewModel @Inject constructor(val remoteDataSource: RemoteDataSource) :
    ViewModel() {
    val worldInfoLiveData = MutableLiveData<WorldCovidInfo>()
    val historyLiveData = MutableLiveData<WorldHistoryInfo>()
    val errorLiveData = MutableLiveData<Exception>()
    val isLoadingLiveData = MutableLiveData<Boolean>(false)

    fun onRefreshButtonClickAll() {
        //запуск корутины
        viewModelScope.launch {
            isLoadingLiveData.value = true
            try {
                val worldInfo = remoteDataSource.getWorldInfo()
                val yesterday = remoteDataSource.yesterdayWorldInfo()
                val twoDaysAgo = remoteDataSource.twoDaysAgoWorldInfo()
                Log.d("myLog", worldInfo.toString())
                worldInfoLiveData.value = worldInfo
                val history = WorldHistoryInfo(
                    worldInfo, yesterday, twoDaysAgo
                )
                historyLiveData.value = history
            } catch (e: Exception) {
                errorLiveData.value = e
            }
            isLoadingLiveData.value = false

        }
    }
}
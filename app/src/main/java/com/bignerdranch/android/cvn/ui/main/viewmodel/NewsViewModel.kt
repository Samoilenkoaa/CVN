package com.bignerdranch.android.cvn.ui.main.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bignerdranch.android.cvn.LoadingResult
import com.bignerdranch.android.cvn.data.News
import com.bignerdranch.android.cvn.remotedatasource.RemoteDataSourceNews
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(val remoteDataSourceNews: RemoteDataSourceNews) :
    ViewModel() {

    val newsLiveData = MutableLiveData<LoadingResult<List<News>>>()
    val isLoadingLiveData = MutableLiveData<Boolean>(false)

    fun refreshNews() {
        viewModelScope.launch {
            isLoadingLiveData.value = true
            val newsAll = remoteDataSourceNews.getAllNewsInfo()
            newsLiveData.value = newsAll
            isLoadingLiveData.value = false
        }
    }
}
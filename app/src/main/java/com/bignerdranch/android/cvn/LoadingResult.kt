package com.bignerdranch.android.cvn

import java.lang.Exception

sealed class LoadingResult<out R> {
    class Success<out T>(val data: T) : LoadingResult<T>()
    class Error(val e: Exception) : LoadingResult<Nothing>()
}
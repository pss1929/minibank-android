package com.pooja.minibank.core.utils

sealed  class ResponseState<T> {
     class Success<T>(val data : T) : ResponseState<T>()
     class Error<T> (val message : T) : ResponseState<T>()
    class Loading<Nothing>() : ResponseState<Nothing>()
}
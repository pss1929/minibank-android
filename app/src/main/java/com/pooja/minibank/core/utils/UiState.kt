package com.pooja.minibank.core.utils

sealed class UiState<out T> {

    data class Success<T>(val data : T) : UiState<T>()
    object Empty : UiState<Nothing>()
    object Loading : UiState<Nothing>()
    data class Error(val message : String) : UiState<Nothing>()
}
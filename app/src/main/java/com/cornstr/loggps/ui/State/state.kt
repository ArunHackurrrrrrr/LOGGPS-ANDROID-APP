package com.cornstr.loggps.ui.State

sealed class UiState<out T>{
    object Loading : UiState<Nothing>()
    data class Success<out T>(val data:T): UiState<T>()
    data class Error(val message: String): UiState<Nothing>()
}



sealed class Add_user_data_UiState<out T>{
    object Loading : Add_user_data_UiState<Nothing>()
    data class Success<out T>(val data:T): Add_user_data_UiState<T>()
    data class Error<out T>(val response: T): Add_user_data_UiState<T>()
}


sealed class Create_Company_UiState<out T>{
    object Loading : Create_Company_UiState<Nothing>()
    data class Success<out T>(val data:T): Create_Company_UiState<T>()
    data class Error<out T>(val response: T): Create_Company_UiState<T>()
}
package com.cornstr.loggps.ui.ViewModels

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cornstr.loggps.data.repository.auth_Credential
import com.cornstr.loggps.data.repository.auth_Response
import com.cornstr.loggps.data.repository.remoteRepository
import com.cornstr.loggps.data.repository.signup_credential
import kotlinx.coroutines.launch
import com.cornstr.loggps.data.auth.TokenManager
import com.cornstr.loggps.data.repository.TokenData
import com.cornstr.loggps.data.repository.signup
import com.cornstr.loggps.ui.State.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow


class APIViewModel: ViewModel() {

    val TokenManager = TokenManager()
    private val _remoteRepo = remoteRepository()
    val TokenManager_Response = mutableStateOf(TokenData())
    val tokenManager_Share_Response : State<TokenData> get() = TokenManager_Response
    var loginResponse : State<auth_Response> = _remoteRepo.loginRes
    private val _uiState = MutableStateFlow<UiState<auth_Response>>(UiState.Loading)
    val uiState: StateFlow<UiState<auth_Response>> = _uiState

    init {
        try {
            Log.d("AAAAADD","DDD")
            auth()
        }
        catch (e: Exception){
            Log.d("theeeee 37","$e")
        }
    }

    fun auth(){
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            TokenManager.get_Tokens(){
                response ->
                Log.d("AXVPGC","$response")
                TokenManager_Response.value = response.copy(
                    TokenStatus = response.TokenStatus,
                    error = response.error,
                    access = response.access
                )
                if (TokenManager_Response.value.TokenStatus == "AVAILABLE"){
                    viewModelScope.launch {
                        _remoteRepo.Tokenlogin(TokenManager_Response.value.access.toString())
                        Log.d("TTRR 58","${loginResponse.value}")
                        _uiState.value = UiState.Success(loginResponse.value)
//                        if (loginResponse.value.loading ==false && loginResponse.value.message.isNotEmpty()){
//                           _uiState.value = UiState.Success(loginResponse.value)
//                        }
                    }
                }
                if (TokenManager_Response.value.TokenStatus == "NIL"){
                    Log.d("AAAAKKK","DDD")
                    _uiState.value =UiState.Error("No Token Found")
                    Log.d("ddddccc","${_uiState.value}")
                }
            }
            Log.d("AXVPGL","${TokenManager_Response.value}")

        }

    }

    fun login(credential: auth_Credential){
        viewModelScope.launch {
            _remoteRepo.token_Access(credential){
                onResult ->
                TokenManager_Response.value = TokenManager_Response.value.copy(
                    TokenStatus = onResult.TokenStatus
                )
            }
        }
    }


    private val _signuUpResponse = mutableStateOf(signup())
    var signUpResponse : State<signup> = _signuUpResponse
    val login_cred_Auth = mutableStateOf(TokenData())

    fun signUp(credential: signup_credential,remoteRepository: remoteRepository){
        viewModelScope.launch {
            try {
                Log.d("INSIDE SIGN UP VIEW MODEL","ST")
                remoteRepository.signUp(credential){
                        onResult ->
                    _signuUpResponse.value = _signuUpResponse.value.copy(
                        loading = false,
                        error = onResult.error,
                        response = onResult.response
                    )
                }
                Log.d("INSIDE SIGN UP VIEW MODEL","ST")

                Log.d("response in viewModel of login", "${remoteRepository.SignUpResponse}")
            } catch (e: Exception) {
                login_cred_Auth.value = login_cred_Auth.value.copy(
                    TokenStatus = "INVALID_CRED"
                )
                Log.d("exception in loginNew", "$e")
            }
        }

    }
}

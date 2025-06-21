package com.cornstr.loggps.ui.ViewModels

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.cornstr.loggps.data.repository.localRepository
import com.cornstr.loggps.data.repository.remoteRepository
import com.cornstr.loggps.data.repository.user_Data
import com.cornstr.loggps.data.repository.user_details_response
import com.cornstr.loggps.ui.State.UiState
import com.cornstr.loggps.ui.screen.add_user_details
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import com.cornstr.loggps.data.auth.TokenManager
import com.cornstr.loggps.data.remote.logGpsService
import com.cornstr.loggps.ui.State.Add_user_data_UiState
import kotlinx.coroutines.suspendCancellableCoroutine
import java.io.File
import kotlin.coroutines.resume



class AddUserDetailsViewModel: ViewModel() {
    private val _remote_Repo = remoteRepository()
    private val _tokenManager = TokenManager()
    private val _user_details = MutableStateFlow<Add_user_data_UiState<user_details_response>>(
        Add_user_data_UiState.Loading)
    var user_details : StateFlow<Add_user_data_UiState<user_details_response>> = _user_details

    suspend fun getAccessTokenSuspend(tokenManager: TokenManager): String =
        suspendCancellableCoroutine { cont ->
            tokenManager.get_Tokens { result ->
                cont.resume(result.access.toString())
            }
        }



    fun add_detials(userDetails: user_Data) {
    var user_detail_add_response  = mutableStateOf(user_details_response())
        viewModelScope.launch{
            var accessToken = getAccessTokenSuspend(_tokenManager)

            try {
                if (accessToken.isNotEmpty()){
                _remote_Repo.add_user_details(accessToken,userDetails) {
                    onResult ->
                    user_detail_add_response.value = user_detail_add_response.value.copy(
                        status = onResult.status,
                        message = onResult.message
                    )
                    if (onResult.message == "detail-added"){
                        _user_details.value = Add_user_data_UiState.Success(user_detail_add_response.value)
                        Log.d("ONRESULT AUDVM 51","$onResult")
                    }
                    else{
                    _user_details.value = Add_user_data_UiState.Error(onResult)
                    Log.d("ONRESULT ERROR SEND TO SCREN","${onResult.message.toString()}")
                }
                }
            }}catch (e: Exception){
                Log.d("Add user Details 31","$e")
            }
        }

    }


    fun add_user_pfp(profilePicture : File){
        Log.d("84-ERR-AUDVM","$profilePicture")
        viewModelScope.launch {
            var accessToken = getAccessTokenSuspend(_tokenManager)
            try {
                Log.d("ERR-IMG-AUDVM-88","$accessToken")
                _tokenManager.get_Tokens() {
                    onResult->
                    accessToken = onResult.access.toString()
                    Log.d("ERR-IMG-AUDVM-92","$onResult")
                }
            }catch (e: Exception){
                Log.d("ERR ACC-TOKEN-PFP","$e")
            }
            try {
                Log.d("ERR-IMG-AUDVM-98","$$")
                if (accessToken.isNotEmpty())
                {   Log.d("ERR-IMG-AUDVM-100","givingpfp")
                    _remote_Repo.add_user_profile_pic(accessToken,profilePicture) {
                        onResult ->
                        Log.d("ONRESULT_PFP","$onResult")
                    }
                }
            }catch (e : Exception){
                Log.d("ERR ADD-PFP-","$e")
            }

        }

    }

}
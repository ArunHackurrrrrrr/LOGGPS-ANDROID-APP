package com.cornstr.loggps.ui.ViewModels

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cornstr.loggps.data.auth.TokenManager
import com.cornstr.loggps.data.repository.company_Create_Response
import com.cornstr.loggps.data.repository.company_details
import com.cornstr.loggps.data.repository.remoteRepository
import com.cornstr.loggps.ui.State.Create_Company_UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

class AdminViewModel : ViewModel() {

    private val _company_get = MutableStateFlow<Create_Company_UiState<company_details>>(
        Create_Company_UiState.Loading)
    var company_get : StateFlow<Create_Company_UiState<company_details>> = _company_get

    private val _tokenManager = TokenManager()
    private val companyDetails = mutableStateOf(company_details())
    suspend fun getAccessToken(): String =
        suspendCancellableCoroutine { cont->
            _tokenManager.get_Tokens {
                    token->
                cont.resume(token.access.toString())
            }
        }


    private val _remoteRepo  = remoteRepository()
    init {
        viewModelScope.launch {
            try {
                val access_tokens = getAccessToken()

                val response = _remoteRepo.get_Company_Details (access_tokens){
                    details ->
                    companyDetails.value = companyDetails.value.copy(
                        name = details.name,
                        address = details.address,
                        company_phone = details.company_phone,
                        company_emial = details.company_emial,
                        latitude = details.latitude,
                        longitude = details.longitude,
                        company_Reg_Num = details.company_Reg_Num,
                        descreption = details.descreption,
                        parameter = details.parameter,
                        is_active = details.is_active
                    )
                    Log.d("ADMIN-VM-55","$details")

                    if (companyDetails.value.name !="ERROR"){
                        _company_get.value = Create_Company_UiState.Success(companyDetails.value)
                    }
                    if (companyDetails.value.name =="ERROR"){
                        _company_get.value = Create_Company_UiState.Error(companyDetails.value)
                    }

                }



            }catch (e:Exception){
                Log.d("ADMIN-VM-34 CATCH","$e")
            }
        }
    }
}
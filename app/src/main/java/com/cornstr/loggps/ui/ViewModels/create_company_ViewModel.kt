package com.cornstr.loggps.ui.ViewModels

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cornstr.loggps.data.auth.TokenManager
import com.cornstr.loggps.data.repository.company_Create_Response
import com.cornstr.loggps.data.repository.company_details
import com.cornstr.loggps.data.repository.remoteRepository
import com.cornstr.loggps.data.repository.user_details_response
import com.cornstr.loggps.ui.State.Add_user_data_UiState
import com.cornstr.loggps.ui.State.Create_Company_UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import java.io.File
import kotlin.coroutines.resume

class create_company_ViewModel: ViewModel() {
    private val _tokenManager = TokenManager()
    private val _remoteRepo = remoteRepository()
    private val _company_create_response = MutableStateFlow<Create_Company_UiState<company_Create_Response>>(
        Create_Company_UiState.Loading)
    var company_create_response : StateFlow<Create_Company_UiState<company_Create_Response>> = _company_create_response

    suspend fun getAccessToken(): String =
        suspendCancellableCoroutine { cont->
            _tokenManager.get_Tokens {
                token->
                cont.resume(token.access.toString())
            }
        }

    fun add_Company_Details(details: company_details,logo: File){
        var accessToken: String
        var create_Response : MutableState<company_Create_Response> = mutableStateOf(company_Create_Response() )

        viewModelScope.launch {
            try {
                accessToken = getAccessToken()
                if (accessToken.isNotEmpty()){
                    _remoteRepo.create_Company(accessToken,details,logo) {
                        response->
                        try {
                            Log.d("COMP-48-DEBUG","$response")
                            create_Response.value = create_Response.value.copy(
                                status = response.status,
                                message = response.message
                            )
                            if (create_Response.value.status == true) {
                                _company_create_response.value =
                                    Create_Company_UiState.Success(create_Response.value)
                                Log.d("COMP-56-DEBUG","$response")
                            }

                            if (create_Response.value.status == false) {
                                _company_create_response.value =
                                    Create_Company_UiState.Error(create_Response.value)
                                Log.d("COMP-62-DEBUG","$response")
                            }

                        }catch (e: Exception){
                            Log.d("COMP-VM-62 CATCH","$e")
                        }
                    }

                }
            }
            catch (e: Exception){
                Log.d("COMP-VM-31 CATCH","$e")
            }
        }


    }
}
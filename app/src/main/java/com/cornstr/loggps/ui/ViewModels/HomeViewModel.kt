package com.cornstr.loggps.ui.ViewModels

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cornstr.loggps.data.local.Graph
import com.cornstr.loggps.data.repository.JwtToken
import com.cornstr.loggps.data.repository.remoteRepository
import com.cornstr.loggps.data.repository.user_Data
import com.cornstr.loggps.ui.State.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeViewModel: ViewModel(){
    private val _localRepo = Graph.Dao
    private val _remoteRepo = remoteRepository()
    private val _uiState = MutableStateFlow<UiState<user_Data>>(UiState.Loading)
    val uiState: StateFlow<UiState<user_Data>> = _uiState
    private val _user_Data = mutableStateOf(user_Data())


    init {
        Log.d("HVM15","IT'S LAUNCHING HOME VIEW MODEL!")
        viewModelScope.launch {
            Log.d("HVM17","ABOVE _LOCAL REPO")
            val tokens = _localRepo.getTokens()
            Log.d("HVM19","ABOVE _LOCAL REPO")
            try {
                _uiState.value = UiState.Loading
                _remoteRepo.get_user_data(tokens.access_Token.toString()){
                    onResult ->
                    Log.d("HOME VIEW MODEL 33 ","${onResult}")
                    if (onResult.name != null){
                        _user_Data.value = _user_Data.value.copy(
                            user = onResult.user,
                            is_company_admin = onResult.is_company_admin,
                            name = onResult.name,
                            gov_No = onResult.gov_No,
                            address = onResult.address,
                            contact_No = onResult.contact_No,
                            role = onResult.role,
                            created_at = onResult.created_at,
                            updated_at = onResult.updated_at,
                            profile_Picture = onResult.profile_Picture
                        )
                        _uiState.value = UiState.Success(_user_Data.value)
                        Log.d("HOME VIEW MODEL 38","${_uiState.value}")
                    }
                    if (onResult.message != null){
                        _user_Data.value = _user_Data.value.copy(
                            message = onResult.message
                        )
                        Log.d("HOME VIEW MODEL 53","${_user_Data.value}")
                        _uiState.value = UiState.Success(_user_Data.value)
                    }
                    else{
                        Log.d("HOME VIEW MODE 57","SOME ERROR")
                    }
                }
//                Log.d("HVM22","$_remoteRepo")
//                UiState.Success("halooo")
//                if (_user_Data.value.user != null){
//                    _uiState.value = UI
//                }
            }catch (e: Exception){
                Log.d("HVM20","$e")
            }

        }
    }
}
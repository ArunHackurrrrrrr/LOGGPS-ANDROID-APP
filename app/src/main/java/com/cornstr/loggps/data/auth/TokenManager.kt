package com.cornstr.loggps.data.auth

import android.util.Log
import android.view.View
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cornstr.loggps.data.local.Dao
import com.cornstr.loggps.data.local.DaoFun
import com.cornstr.loggps.data.repository.auth_Credential
import com.cornstr.loggps.data.repository.localRepository
import com.cornstr.loggps.data.repository.remoteRepository
import com.cornstr.loggps.data.local.Graph
import com.cornstr.loggps.data.remote.logGpsService
import com.cornstr.loggps.data.repository.JwtToken
import com.cornstr.loggps.data.repository.TokenData
import com.cornstr.loggps.data.repository.auth_Response
import com.cornstr.loggps.data.repository.refreshTokensRequest
import com.cornstr.loggps.data.repository.refreshTokensResponse
import kotlinx.coroutines.launch

class TokenManager: ViewModel(){
    private val _localRepo = Graph.Dao
    private var _refreshRequest= mutableStateOf(refreshTokensRequest())
    private val jwtToken = mutableStateOf(JwtToken())

    fun get_Tokens(onResult:(TokenData) -> Unit)
    {
        viewModelScope.launch {
            try {
                Log.d("AXVPGA","INSIDE TOKEN MANAGER !")
                val refreshTokenRespone = mutableStateOf(refreshTokensResponse())
                val tokens = mutableStateOf(refreshTokensRequest())
                val stored_Tokens = _localRepo.getTokens()

                Log.d("AXVPGA_STORED_TOKENS","$stored_Tokens")


                if(stored_Tokens!=null
                    && stored_Tokens.access_Token.isNotEmpty())
                {
                    try {
                        Log.d("45 TKMANAGER","edkd")
                        val response = logGpsService.login("Bearer ${stored_Tokens.access_Token}")
                        try {

                        onResult(
                            TokenData(
                                TokenStatus = "AVAILABLE",
                                error = "NIL",
                                access = stored_Tokens.access_Token
                            )

                        )}catch (e: Exception){
                            Log.d("to ye hai ","$e")
                        }
//                        Log.d("53 TKMANAGER","$response")
                    }catch (e: Exception) {
                        Log.d("AXVPG_REFRESH_TOKEN_VAL_STORED", "${stored_Tokens.refresh_Token}")
//
                        _refreshRequest.value = _refreshRequest.value.copy(
                            refresh = stored_Tokens.refresh_Token,
                            access = stored_Tokens.access_Token
                        )
                        try {
                            refreshTokenRespone.value =
                                logGpsService.refreshTokens(_refreshRequest.value)
                            logGpsService.login("Bearer ${refreshTokenRespone.value.access.toString()}")

                            Log.d("AXVPG", "IT SHOULD UPDATE")
                            jwtToken.value = jwtToken.value.copy(
                                id = 1,
                                refresh_Token = refreshTokenRespone.value.refresh.toString(),
                                access_Token = refreshTokenRespone.value.access.toString()
                            )
                            Log.d("AXVPA", "ABOVE UPDATE")
                            _localRepo.updateTokens(jwtToken.value)
                            Log.d("BELOW", "UPDATE")


                            onResult(
                                TokenData(
                                    TokenStatus = "AVAILABLE",
                                    error = "NIL",
                                    access = "${refreshTokenRespone.value.access}"
                                )
                            )
                        }catch (e: Exception){
                            Log.d("83REFRESH_FAIL","REFRESH FAIL -> $e")
                            onResult(TokenData(TokenStatus = "NIL", error = "not in db"))
                        }
                    }
                }
                else{
                    Log.d("AXVPGB","THE ELSE HERE !@#")
                    onResult(TokenData(TokenStatus = "NIL", error = "not in db"))

                }
            }catch (e: Exception){
                Log.d("dddddddddd","$e")
            }
        }
    }


}




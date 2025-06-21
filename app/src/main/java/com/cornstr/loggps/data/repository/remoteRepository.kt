package com.cornstr.loggps.data.repository

import androidx.lifecycle.ViewModel
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import coil.network.HttpException
import com.cornstr.loggps.data.remote.logGpsService
//import com.cornstr.loggps.data.auth.TokenManager
import com.cornstr.loggps.data.local.Graph
import com.google.gson.Gson
//import com.cornstr.loggps.ui.Token
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.ResponseBody.Companion.toResponseBody
import java.io.File
import java.lang.Exception
import kotlin.math.log

class remoteRepository : ViewModel() {


    //    TODO LOGIN
    private val _login = mutableStateOf(auth_Response("", ""))
    val loginRes: State<auth_Response> get() = _login
    fun Tokenlogin(data: String) {
        Log.d("AXVPGR", "LOGIN FUN LAUNCHED")
        viewModelScope.launch {
            try {
                val datas = logGpsService.login("Bearer $data")
                _login.value = _login.value.copy(
                    message = datas.message,
                    status = datas.status,
                    code = datas.code,
                    detail = datas.detail,
                    messages = datas.messages

                )
                Log.d("newRepoka", "${datas.message}")

            } catch (e: Exception) {
                Log.d("logError here ", "$e")

            }
        }
    }


    //TODO SIGNUP
    private val _SignUpApiResponse = mutableStateOf(signup())
    val SignUpResponse: State<signup> get() = _SignUpApiResponse

    fun signUp(data: signup_credential, onResult: (signup) -> Unit) {
        viewModelScope.launch {
            try {
                Log.d("INSIDE SIGNUP REMOTE", "ST")
                val response = logGpsService.signUp(data)
                _SignUpApiResponse.value = _SignUpApiResponse.value.copy(
                    loading = false,
                    error = response.error,
                    response = response.status
                )
                Log.d("INSIDE SIGNUP REMOTE", "END")
                Log.d("signup_done", "${response}")
                onResult(
                    signup(
                        loading = false,
                        error = response.error,
                        response = response.status
                    )
                )
            } catch (e: kotlin.Exception) {
                _SignUpApiResponse.value = _SignUpApiResponse.value.copy(
                    loading = false,
                    error = "$e",
                    response = ""
                )

                Log.d("singup_error", "$e")
            }
        }
    }


    //TODO CREDENTIAL LOGIN
    private val _id_Login = mutableStateOf(authStatus())
    val id_Login_Response: State<authStatus> get() = _id_Login
    private val _localRepo = Graph.Dao
    val jwtToken = mutableStateOf(JwtToken())

    fun token_Access(credential: auth_Credential, onResult: (TokenData) -> Unit) {

        viewModelScope.launch {
            try {


                val respones = logGpsService.userAtuhentication(credential)
                _id_Login.value = _id_Login.value.copy(
                    access = respones.access,
                    refresh = respones.refresh
                )
                try {
                    Log.d("AXVPG_CHECK_NULL", "$id_Login_Response")
                    if (id_Login_Response.value != null) {
                        Log.d("AXVPG_IF_ID_LOG", "$id_Login_Response")
                        Tokenlogin(id_Login_Response.value.access.toString())
                        jwtToken.value = jwtToken.value.copy(
                            id = 1,
                            access_Token = id_Login_Response.value.access.toString(),
                            refresh_Token = id_Login_Response.value.refresh.toString()
                        )
                        _localRepo.addTokens(jwtToken.value)
                    }
                } catch (e: Exception) {
                    Log.d("AXVPG_ID_LOGIN", "$e")
                }
            } catch (e: Exception) {
                Log.d("AXVPGA_INVALID", "$e")
                onResult(
                    TokenData(TokenStatus = "INVALID_CRED")
                )
            }
        }
    }


    fun get_user_data(accessToken: String, onResult: (user_Data) -> Unit) {
        viewModelScope.launch {
            try {
                val response = logGpsService.get_User_Data("Bearer $accessToken")

                Log.d("REMOTE REPO 119", "${response}")
                if (response.message == null) {
                    onResult(
                        user_Data(
                            user = response.user,
                            is_company_admin = response.is_company_admin,
                            name = response.name,
                            gov_No = response.gov_No,
                            address = response.address,
                            contact_No = response.contact_No,
                            role = response.role,
                            profile_Picture = response.profile_Picture
                        )
                    )
                } else {
                    onResult(user_Data(message = response.message))
                }
            } catch (e: Exception) {
                Log.d("RREPO127", "$e")
            }
        }
    }


    fun add_user_details(
        accessToken: String,
        user_Data: user_Data,
        onResult: (user_details_response) -> Unit
    ) {
        try {
            viewModelScope.launch {
                val response = logGpsService.add_User_Data("Bearer $accessToken", user_Data)
                if (response.status != null) {
                    onResult(
                        user_details_response(
                            message = response.message,
                            status = response.status
                        )
                    )
                } else (
                        onResult(
                            user_details_response(
                                message = response.message,
                                status = "not-added"
                            )
                        )
                        )
            }
        } catch (e: kotlin.Exception) {
            Log.d("remote repo 140", "$e")
        }
    }

    fun add_user_profile_pic(
        accessToken: String,
        profile_picture: File,
        onResult: (user_details_response) -> Unit
    ) {
        viewModelScope.launch {
            val profile_processed = prepareFile("profile_Picture", profile_picture)
            try {
                Thread.setDefaultUncaughtExceptionHandler { thread, throwable ->
                    Log.e("CRASH194", "Uncaught exception in thread ${thread.name}: ${throwable.message}", throwable)
                }

                val response = logGpsService.add_User_Image("Bearer $accessToken",profile_processed)
                onResult(user_details_response(message = response.message, status = response.status))
                Thread.setDefaultUncaughtExceptionHandler { thread, throwable ->
                    Log.e("CRASH200", "Uncaught exception in thread ${thread.name}: ${throwable.message}", throwable)
                }


            }catch (e:Exception){
                Log.d("The error is ","$e")
            }catch (e: HttpException){
                Log.d("HTTP1111 EXCEPTION HAI ","$e")
            }
        }

    }


        //TODO PREPARE IMAGE FILE TO SEND FROM RETROFIT TO SERVER

        fun prepareFile(partName: String, file: File): MultipartBody.Part {
            Log.d("RMREPO-180", "$file")
            val requestFile = RequestBody.create("image/*".toMediaTypeOrNull(), file)
            return MultipartBody.Part.createFormData(partName, file.name, requestFile)
        }





    fun create_Company(acceToken: String, data : company_details, logo : File,onResult: (company_Create_Response) -> Unit){
        val gson = Gson()
        viewModelScope.launch {
            try {
                val logo = prepareFile("company_logo",logo)
                val json = gson.toJson(data)
                val dataBody = json.toRequestBody("user_data/json".toMediaType())
                
                val response = logGpsService.create_Company("Bearer $acceToken",dataBody,logo)
                Log.d("REMOTE-REPO-LOG-GPS-241","$response")
                onResult(response)
            }catch (e: Exception){
                Log.d("REMOTE-REPO-LOG-GPS 233","$e")
            }
        }
    }




    fun get_Company_Details(accessToken: String,onResult: (company_details) -> Unit){
        viewModelScope.launch {
            try {
                val response = logGpsService.get_Company("Bearer $accessToken")
                onResult(response)
            } catch (e: Exception) {
                onResult(company_details(name = "ERROR"))
                Log.d("REMOTE-REPO-LOG-GPS-256", "$e")
            }
        }
    }


}
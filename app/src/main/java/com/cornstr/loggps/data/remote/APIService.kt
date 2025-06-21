package com.cornstr.loggps.data.remote

import android.R
import android.media.Image
import com.cornstr.loggps.data.repository.auth_Credential
import com.cornstr.loggps.data.repository.auth_Response
import com.cornstr.loggps.data.repository.authStatus
import com.cornstr.loggps.data.repository.signUpResponse
import com.cornstr.loggps.data.repository.APISConstants
import com.cornstr.loggps.data.repository.refreshTokensRequest
import com.cornstr.loggps.data.repository.refreshTokensResponse
import com.cornstr.loggps.data.repository.signup_credential
import com.cornstr.loggps.data.repository.user_Data
import com.cornstr.loggps.data.repository.company_Create_Response
import com.cornstr.loggps.data.repository.user_details_response
import com.cornstr.loggps.data.repository.company_details
import okhttp3.MultipartBody
//import retrofit2.Retrofit
//import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part


import okhttp3.OkHttpClient
import okhttp3.RequestBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

// Logging interceptor
private val loggingInterceptor = HttpLoggingInterceptor().apply {
    level = HttpLoggingInterceptor.Level.BODY  // Logs request & response body
}

// OkHttp client with interceptor
private val okHttpClient = OkHttpClient.Builder()
    .addInterceptor(loggingInterceptor)
    .build()

// Retrofit instance with custom client
private val retrofit = Retrofit.Builder()
    .baseUrl(APISConstants.base_Url)
    .client(okHttpClient)
    .addConverterFactory(GsonConverterFactory.create())
    .build()

val logGpsService: APIService = retrofit.create(APIService::class.java)


interface APIService{
    //TODO LOGIN API
    @POST(APISConstants.login_End_Point)
    suspend fun login(
        @Header("Authorization") request: String) : auth_Response

    //TODO GET TOKEN API
    @POST(APISConstants.get_token)
    suspend fun userAtuhentication(@Body request: auth_Credential) : authStatus

    //TODO SIGNUP API
    @POST(APISConstants.sign_Up_End_Point)
    suspend fun signUp(@Body request: signup_credential) : signUpResponse

    @POST(APISConstants.refresh_token)
    suspend fun refreshTokens(@Body request: refreshTokensRequest) : refreshTokensResponse

    @GET(APISConstants.home_End_Point)
    suspend fun get_User_Data(
        @Header("Authorization") request: String) : user_Data

    @POST(APISConstants.add_User_Details)
    suspend fun add_User_Data(
        @Header("Authorization") token: String,
        @Body details : user_Data) : user_details_response


    @Multipart
    @POST(APISConstants.add_User_Details_Profile_Picture)
    suspend fun add_User_Image(
        @Header("Authorization") token: String,
        @Part image: MultipartBody.Part
    ) : user_details_response

    @Multipart
    @POST(APISConstants.create_Company)
    suspend fun create_Company(
        @Header("Authorization") token: String,
        @Part("data") data : RequestBody,
        @Part image: MultipartBody.Part
    ) : company_Create_Response


    @GET(APISConstants.get_Company)
    suspend fun  get_Company(
        @Header("Authorization") token : String,
    ) : company_details

}

// TODO DEKHO UPAR JO POST REQUEST MARI HAI END-POINT PAR JO TRIGGER KAR KAR RHA HAI EK BELOW KA SUSPEND FUN JIKO HUM VIEWMODEL ME CALL OUT KARENGE
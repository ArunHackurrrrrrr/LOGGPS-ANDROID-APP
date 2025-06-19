package com.cornstr.loggps.data.repository
import android.R
import android.content.pm.PackageInstaller
import android.os.Parcelable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.compose.NavHost
import kotlinx.parcelize.Parcelize


@Parcelize
data class auth_Response(
    val message : String,
    val status : String,
    val code : String?=null,
    val detail: String?=null,
    val messages : List<unauthMsg>?=null,
    val loading: Boolean = false,

): Parcelable

@Parcelize
data class unauthMsg(
    val token_class : String?=null,
    val token_type : String? = null,
    val message : String?=null
): Parcelable

@Parcelize
data class authStatus(
    val access : String?=null,
    val refresh : String?=null
): Parcelable

data class auth_Credential(
    val username : String,
    val password : String
)

@Parcelize
data class signUpResponse(
    val status : String,
    val error : String
) : Parcelable

data class signup_credential(
    val user_Mail : String,
    val user_Password : String
)

data class login(
    val loading : Boolean = true,
    val response: String = "",
    val status_Auth : String = "",
    val error : String?=null
)

data class TokenData(
    val TokenStatus : String = "",
    val error : String = "",
    val access: String?=null
)

data class signup(
    val loading : Boolean = true,
    val error : String = "",
    val response : String = ""
)

@Parcelize
data class refreshTokensResponse(
    val access: String?=null,
    val refresh: String?=null
): Parcelable


data class refreshTokensRequest(
    val access: String?=null,
    val refresh: String?=null
)

@Parcelize
data class  user_Data(
    val user : String ?=null,
    val is_company_admin : String ?=null,
    val name : String ?= null,
    val gov_No : String ?= null,
    val address : String ?= null,
    val contact_No : String ?= null,
    val role : String ?=null,
    val created_at : String ?=null,
    val updated_at : String ?=null,
    val profile_Picture : String ?=null,
    val message : String ?=null
):Parcelable


@Parcelize
data class  user_details_response(
    val message: String ?= null,
    val status: String ?=null
): Parcelable


@Parcelize
data class company_details(
    val name : String ?=null,
    val descreption : String ?=null,
    val address : String ?=null,
    val company_phone : String ?=null,
    val company_emial : String ?=null,
    val latitude : Float ?=null,
    val longitude : Float ?=null,
    val parameter : Int ?=null,
    val is_active : Boolean ?=null,
    val company_Reg_Num : String ?=null
) : Parcelable

@Parcelize
data class company_Create_Response(
    val status : Boolean ?=null,
    val message : String ?=null
): Parcelable
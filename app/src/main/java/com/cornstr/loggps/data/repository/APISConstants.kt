package com.cornstr.loggps.data.repository

object APISConstants{
    const val base_Url = "http://192.168.132.48:8000//"  // TODO YOUR DOMAIN HERE !!
    const val get_token = "/api/token/"
    const val refresh_token = "/api/token/refresh/"
    const val login_End_Point = "auth/login/"
    const val sign_Up_End_Point = "/auth/signUp"
    const val home_End_Point = "dashboard/get-user-details/"
    const val add_User_Details = "dashboard/add-user-details/"
    const val add_User_Details_Profile_Picture = "dashboard/add-user-image/"
}
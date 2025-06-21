package com.cornstr.loggps.ui.navigation

import android.graphics.pdf.PdfDocument
import androidx.annotation.DrawableRes
import com.cornstr.loggps.R

sealed class Screen(val title : String, val route : String) {
    sealed class Pages(val pTitle : String, pRoute : String, @DrawableRes val icon: Int): Screen(pTitle,pRoute)
    {
        object loading_page : Pages("Loading","loading_page", R.drawable.ic_launcher_foreground)

        object Login_Page : Pages("Login", "login_page", R.drawable.ic_profile)

        object  Home_Page : Pages("Home", "home_page", R.drawable.ic_home)

        object Todo_Page : Pages("Todo", "admin_page", R.drawable.ic_notes)

        object SignUp_Page : Pages("SignUp","signup_page", R.drawable.ic_launcher_foreground)

        object User_Profile : Pages("Profile","user_profile_page", R.drawable.ic_profile)

        object User_Details_Add : Pages("Details","add_user_details_page", R.drawable.ic_lock)

        object Company_Details_Add : Pages("AddCompany","add_company_details_page",R.drawable.ic_notes)

        object Admin_Page : Pages("AdminPage","admin_page",R.drawable.ic_profile)
    }
}


val page_Screen = listOf(
    Screen.Pages.Todo_Page,
    Screen.Pages.Home_Page,
    Screen.Pages.User_Profile
)
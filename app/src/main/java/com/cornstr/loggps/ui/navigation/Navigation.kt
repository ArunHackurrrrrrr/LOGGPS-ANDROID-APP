package com.cornstr.loggps.ui.navigation

import android.util.Log
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.cornstr.loggps.data.repository.company_details
import com.cornstr.loggps.ui.ViewModels.APIViewModel
import com.cornstr.loggps.ui.ViewModels.HomeViewModel
import com.cornstr.loggps.ui.screen.Admin_Dashboard
import com.cornstr.loggps.ui.screen.add_user_details
import com.cornstr.loggps.ui.screen.company_Registration_Page
//import com.cornstr.loggps.ui.UIViewModel
import com.cornstr.loggps.ui.screen.homeScreen
import com.cornstr.loggps.ui.screen.loading_Screen
import com.cornstr.loggps.ui.screen.loginScreen
import com.cornstr.loggps.ui.screen.signUpScreen

@Composable
fun NavigateScreen(navController: NavController, scaffoldPaddingValue : PaddingValues){
    val viewModel: APIViewModel = viewModel()
    NavHost(navController = navController as NavHostController,
        modifier = Modifier.padding(scaffoldPaddingValue),
        startDestination = Screen.Pages.loading_page.route)
    {
        composable(Screen.Pages.loading_page.route){
            Log.d("DCDE","FORWAR LOADER PG")
            loading_Screen(viewModel,navController)
        }

        composable(Screen.Pages.Login_Page.route){
            Log.d("DCDE","FORWARD LOADER LOGIN")
            loginScreen(navController,viewModel)
        }
        composable(Screen.Pages.Home_Page.route){
            homeScreen(scaffoldPaddingValue = scaffoldPaddingValue,navController)
        }
        composable(Screen.Pages.SignUp_Page.route){
            signUpScreen(navController,viewModel)
        }
        composable(Screen.Pages.User_Details_Add.route){
            add_user_details(modifier = Modifier,navController)
        }
        composable(Screen.Pages.Company_Details_Add.route){
            company_Registration_Page(navController)
        }
        composable(Screen.Pages.Admin_Page.route){
            Admin_Dashboard()
        }

    }
}
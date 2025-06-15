package com.cornstr.loggps.ui.screen

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import com.cornstr.loggps.R
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.cornstr.loggps.data.repository.auth_Credential
import com.cornstr.loggps.ui.State.UiState
import com.cornstr.loggps.ui.ViewModels.APIViewModel
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.placeholder
import com.google.accompanist.placeholder.shimmer

@Composable
fun loginScreen(navController : NavController,viewModel: APIViewModel,modifier: Modifier= Modifier){

//    val remoteRepository = remember { remoteRepository() }
    var userMail : String by remember { mutableStateOf("") }
    var userPass : String by remember { mutableStateOf("") }
    val userCredential = auth_Credential(userMail, userPass)
    val apiState = viewModel.loginResponse.value
    val token_Response by viewModel.tokenManager_Share_Response
    Log.d("login response inside login page","$apiState")
    var isLoading by remember { mutableStateOf("") }
//    val uiState by viewModel.uiState.collectAsState()
//    var show_auto_login_loading by remember { mutableStateOf(false) }

//    when(uiState){
//        is UiState.Error -> TODO()
//        UiState.Loading -> {show_auto_login_loading = true}
//        is UiState.Success<*> -> {show_auto_login_loading = false}
//    }
//
//    if (show_auto_login_loading){
//        loader()
//    }
    if (isLoading == "true"){
        loader()
    }

    Column(modifier = Modifier.padding(top = 8.dp, bottom = 8.dp).fillMaxSize(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
        Text("Login", style = TextStyle(
            fontSize = 40.sp,
            fontWeight = FontWeight.ExtraBold,
            color = Color.Cyan
        ))
            OutlinedTextField(modifier = Modifier.padding(4.dp).fillMaxWidth(),value = userMail,
                onValueChange = {userMail = it}, placeholder = {Row(modifier = Modifier.padding(start = 8.dp).fillMaxWidth()) {
                    Icon(Icons.Default.MailOutline, contentDescription = null)
                    Text("Email Id", modifier = Modifier.padding(start = 8.dp))
                }}, shape = RoundedCornerShape(25.dp))

            OutlinedTextField(modifier = Modifier.padding(4.dp).fillMaxWidth(),value = userPass,
                onValueChange = {userPass = it}, placeholder = {Row(modifier = Modifier.padding(start = 8.dp).fillMaxWidth()) {
                    Icon(painter = painterResource(id = R.drawable.ic_lock), contentDescription = null)
                    Text("Password", modifier = Modifier.padding(start = 8.dp))
                }}, shape = RoundedCornerShape(25.dp))

        Button(onClick = {
                viewModel.login(userCredential)
                         isLoading = "true"}
            , modifier = Modifier.fillMaxWidth().padding(4.dp), shape = RoundedCornerShape(15.dp)) {
            Text("Login")
        }

        if (apiState.loading == false && apiState.message.isNotEmpty() && apiState.status =="Authorised"){
            navController.navigate("home_page"){popUpTo("login_page"){
                inclusive = true
            } }
            isLoading = "false"
        }

        if (token_Response.TokenStatus.isNotEmpty() && token_Response.TokenStatus == "AVAILABLE"){
            Log.d("AXVPGW","THIS IS THE UN AUTH ! - ${token_Response.TokenStatus}")
            isLoading = "true"
            Log.d("LOGIN SCREEN 93","$token_Response")
            navController.navigate("home_page")
            Text("UNAUTHORISED")
        }
        if (token_Response.TokenStatus.isNotEmpty() && token_Response.TokenStatus =="INVALID_CRED"){
            Text("Invalid Credential")
            isLoading = "false"
        }



        TextButton(onClick = {}, modifier = Modifier.padding(bottom = 20.dp)) {
            Text("Forget Password?")
        }

        Row(modifier = Modifier.fillMaxWidth()) {
            Text("Don't have an account ?", modifier = Modifier.padding(start = 50.dp, top = 6.dp))
            TextButton(onClick = {
                navController.navigate("signup_page")
//                uiViewModel.setCurrentScreen(screen = Screen.Pages.Home_Page)

            }) { Text("Sign Up", modifier = Modifier.padding(start = 16.dp, bottom = 14.dp))}
        }

        Divider()

    }

}

@Composable
fun loader(){
    Box(modifier = Modifier.fillMaxSize().background(Color.Black)) {
        Box(modifier = Modifier
            .matchParentSize()
            .fillMaxSize()
            .background(Color.Black)
            .placeholder(
                visible = true,
                highlight = PlaceholderHighlight.shimmer(Color.White),
                color = Color.DarkGray
            )){

        }
    }
}
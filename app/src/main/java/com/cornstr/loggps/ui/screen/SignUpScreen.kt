package com.cornstr.loggps.ui.screen
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import com.cornstr.loggps.data.repository.remoteRepository
import com.cornstr.loggps.data.repository.signup_credential
import com.cornstr.loggps.ui.ViewModels.APIViewModel

//import com.cornstr.loggps.ui.TokenStatus


@Composable
fun signUpScreen(navController : NavController,viewModel: APIViewModel,modifier: Modifier= Modifier){

    var userMail : String by remember { mutableStateOf("") }
    var userPass : String by remember { mutableStateOf("") }
    val userCredential = signup_credential(userMail, userPass)
    val signUpState = viewModel.signUpResponse.value
    var isLoading by remember { mutableStateOf("") }
    val remoteRepository : remoteRepository


    Column(modifier = Modifier.padding(top = 8.dp, bottom = 8.dp).fillMaxSize(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
        Text("Sign-Up", style = TextStyle(
            fontSize = 40.sp,
            fontWeight = FontWeight.ExtraBold,
            color = Color.Cyan
        ))
        OutlinedTextField(modifier = Modifier.padding(4.dp),value = userMail,
            onValueChange = {userMail = it}, placeholder = {Row(modifier = Modifier.padding(start = 8.dp).fillMaxWidth()) {
                Icon(Icons.Default.MailOutline, contentDescription = null)
                Text("Email Id", modifier = Modifier.padding(start = 8.dp))
            }}, shape = RoundedCornerShape(25.dp))

        OutlinedTextField(modifier = Modifier.padding(4.dp),value = userPass,
            onValueChange = {userPass = it}, placeholder = {Row(modifier = Modifier.padding(start = 8.dp).fillMaxWidth()) {
                Icon(painter = painterResource(id = R.drawable.ic_lock), contentDescription = null)
                Text("Password", modifier = Modifier.padding(start = 8.dp))
            }}, shape = RoundedCornerShape(25.dp))

        Button(onClick = {
            viewModel.signUp(userCredential, remoteRepository())
            isLoading = "true"}
            , modifier = Modifier.fillMaxWidth().padding(4.dp), shape = RoundedCornerShape(15.dp)) {
            Text("SignUp")
            Log.d("INSIDE SIGN UP BUTTON","END")
        }

        if (signUpState.loading == false && signUpState.response.isNotEmpty() && signUpState.response =="created"){
            navController.navigate("login_page")
        }
        Log.d("TOKENAuth","$")
        if (signUpState.error.isNotEmpty() && signUpState.response == "notcreated"){
            Text("${signUpState.error}")
            isLoading = "false"}

        if (isLoading == "true"){
            loader()
        }

        Row(modifier = Modifier.fillMaxWidth()) {
            Text("Already have an Account", modifier = Modifier.padding(start = 50.dp, top = 6.dp))
            TextButton(onClick = {navController.navigate("login_page")}) { Text("Login!", modifier = Modifier.padding(start = 16.dp, bottom = 14.dp))}
        }

        Divider()

    }

}

@Composable
fun loaders(modifier: Modifier){
    Column {
        CircularProgressIndicator(modifier = modifier.align(Alignment.CenterHorizontally))
    }
}
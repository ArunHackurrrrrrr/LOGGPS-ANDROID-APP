package com.cornstr.loggps.ui.screen

import android.graphics.drawable.Drawable
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.material3.Button
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.ModalDrawer
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.cornstr.loggps.R
import com.cornstr.loggps.ui.ViewModels.AddUserDetailsViewModel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.input.pointer.motionEventSpy
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.cornstr.loggps.data.repository.user_Data
import com.cornstr.loggps.data.repository.user_details_response
import com.cornstr.loggps.ui.State.Add_user_data_UiState
import com.cornstr.loggps.ui.State.UiState
import com.cornstr.loggps.ui.theme.Add_user_data_page_bg
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.placeholder
import com.google.accompanist.placeholder.shimmer
import com.yourapp.utils.imageExtractor
import java.io.File

@Composable
fun add_user_details(modifier: Modifier = Modifier,navController: NavController){

    var user_name : String by remember { mutableStateOf("") }
    var user_phone : String by remember { mutableStateOf("") }
    var user_address : String by remember { mutableStateOf("") }
    var user_aadhaar : String by remember { mutableStateOf("") }
    var ContacalertColor : Color by remember { mutableStateOf(Color.Transparent) }
    var AadhharalertColor : Color by remember { mutableStateOf(Color.Transparent) }
    var AddressalertColor : Color by remember { mutableStateOf(Color.Transparent) }
    var ContactAlertText : String by remember { mutableStateOf("") }
    var AadhharAlertText : String by remember { mutableStateOf("") }
    var AddressAlertText : String by remember { mutableStateOf("") }
    var profilePicture by remember { mutableStateOf<File?>(null) }
    var loader : Boolean by remember { mutableStateOf(false) }
    var get_image by remember { mutableStateOf(false) }
    val user_details_toSubmit = user_Data(
        name = user_name,
        contact_No = user_phone,
        address = user_address,
        gov_No = user_aadhaar
    )


    val addUserDetailsViewModel: AddUserDetailsViewModel  = viewModel()
    val uistate by addUserDetailsViewModel.user_details.collectAsState()
    var add_details_response by remember { mutableStateOf(user_details_response()) }
//    var error_in_fields by remember { mutableStateOf() }


    when(uistate){
        is Add_user_data_UiState.Loading -> {}
        is Add_user_data_UiState.Success<*> -> {
            val response = (uistate as Add_user_data_UiState.Success).data
            add_details_response = response
            loader = false
            navController.navigate("home_page")

        }
        is Add_user_data_UiState.Error<*> ->{
            val response = (uistate as Add_user_data_UiState.Error).response
            add_details_response = response
            Log.d("AUDS ERROR WHEN","${add_details_response}")
            if (response.message == "contact_No"){
                ContacalertColor = Color.Red
                ContactAlertText = "Invalid Contact No!"
                loader = false
            }
            if (response.message == "gov_No"){
                AadhharalertColor = Color.Red
                AadhharAlertText = "Invalid Aadhhar No.!"
                loader = false
            }
            if (response.message == "address"){
                AddressalertColor = Color.Red
                AddressAlertText = "Invalid Address!"
                loader = false
            }
        }
    }



    Box(modifier = Modifier
        .fillMaxSize()
        .background(Add_user_data_page_bg))
    {
        Column(modifier = Modifier
            .fillMaxWidth()
            .fillMaxSize())
        {
            Box(modifier = Modifier
                .fillMaxWidth()
                .weight(0.3f)
                )
            {
                Column(modifier = Modifier
                    .fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally) {
                    Box() {
                        Box(
                            modifier = Modifier
                                .clip(CircleShape)
                                .background(Color.White)
                                .size(155.dp)
                        ) {
                            //TODO PREVIEW IMAGE
                            profilePicture?.let {
                                Image(
                                    painter = rememberAsyncImagePainter(model = it),
                                    contentDescription = null
                                )
                            }
                        }
                        IconButton(onClick = {
                            get_image = true
                        }, modifier = Modifier.padding(start = 100.dp, top = 110.dp)) {
                            Icon(painterResource(id = R.drawable.ic_profile), contentDescription = null,
                                modifier = Modifier.size(40.dp)
                                    .clip(CircleShape)
                                    .background(Color.LightGray)
                            )
                        }
                    }
                }
            }
            if (get_image){
                get_the_extractor(){
                    Image ->
                    profilePicture = Image
                }
            }
            Divider()
            Box(modifier = Modifier
                .fillMaxWidth()
                .weight(0.7f)
            ){
                Column {
                    OutlinedTextField(modifier = Modifier
                        .padding(top = 16.dp, start = 6.dp, end = 6.dp)
                        .clip(RoundedCornerShape(45.dp))
                        .background(Color.White)
                        .fillMaxWidth()
                        ,
                        value = user_name,
                        placeholder = {
                            Row(
                                modifier = Modifier.fillMaxWidth()
                            )
                            { Icon(painterResource(id = R.drawable.ic_profile), contentDescription = null)
                            Text("Full Name", modifier = Modifier
                                .padding(start = 12.dp, top = 4.dp)
                                .fillMaxWidth()
                                .size(20.dp))}
                        },
                        onValueChange = {user_name = it},
                        shape = RoundedCornerShape(45.dp),
                        textStyle = TextStyle(Color.Black))


                    OutlinedTextField(modifier = Modifier
                        .padding(top = 34.dp, start = 6.dp, end = 6.dp)
                        .clip(RoundedCornerShape(45.dp))
                        .background(Color.White)
                        .fillMaxWidth()
                        .border(color = ContacalertColor, width = 4.dp, shape = RoundedCornerShape(24.dp))
                        ,
                        value = user_phone,
                        placeholder = {
                            Row(
                                modifier = Modifier.fillMaxWidth()
                            )
                            { Icon(painterResource(id = R.drawable.ic_profile), contentDescription = null)
                                Text("Contact No.", modifier = Modifier
                                    .padding(start = 12.dp, top = 4.dp)
                                    .fillMaxWidth()
                                    .size(20.dp))}
                        },
                        onValueChange = {user_phone = it},
                        shape = RoundedCornerShape(45.dp),
                        textStyle = TextStyle(Color.Black))
                    if (ContactAlertText != ""){
                        Text("$ContactAlertText", textAlign = TextAlign.Center, color = Color.Red, fontWeight = FontWeight.SemiBold,modifier= Modifier.padding(top = 2.dp, start = 16.dp))
                    }


                    OutlinedTextField(modifier = Modifier
                        .padding(top = 34.dp, start = 6.dp, end = 6.dp)
                        .clip(RoundedCornerShape(45.dp))
                        .background(Color.White)
                        .fillMaxWidth()
                        .border(color = AddressalertColor, width = 4.dp, shape = RoundedCornerShape(24.dp))
                        ,
                        value = user_address,
                        placeholder = {
                            Row(
                                modifier = Modifier.fillMaxWidth()
                            )
                            { Icon(painterResource(id = R.drawable.ic_profile), contentDescription = null)
                                Text("Address", modifier = Modifier
                                    .padding(start = 12.dp, top = 4.dp)
                                    .fillMaxWidth()
                                    .size(20.dp))}
                        },
                        onValueChange = {user_address = it},
                        shape = RoundedCornerShape(45.dp),
                        textStyle = TextStyle(Color.Black))
                    if (AddressAlertText != ""){
                        Text("$AddressAlertText", textAlign = TextAlign.Center, color = Color.Red, fontWeight = FontWeight.SemiBold,modifier= Modifier.padding(top = 2.dp, start = 16.dp))
                    }


                    OutlinedTextField(modifier = Modifier
                        .padding(top = 34.dp, start = 6.dp, end = 6.dp)
                        .clip(RoundedCornerShape(45.dp))
                        .background(Color.White)
                        .fillMaxWidth()
                        .border(color = AadhharalertColor, width = 4.dp, shape = RoundedCornerShape(24.dp))
                        ,
                        value = user_aadhaar,
                        placeholder = {
                            Row(
                                modifier = Modifier.fillMaxWidth()
                            )
                            { Icon(painterResource(id = R.drawable.ic_profile), contentDescription = null)
                                Text("Aadhaar Card No.", modifier = Modifier
                                    .padding(start = 12.dp, top = 4.dp)
                                    .fillMaxWidth()
                                    .size(20.dp))}
                        },
                        onValueChange = {user_aadhaar = it},
                        shape = RoundedCornerShape(45.dp),
                        textStyle = TextStyle(Color.Black))
                    if (AadhharAlertText != ""){
                        Text("$AadhharAlertText", textAlign = TextAlign.Center, color = Color.Red, fontWeight = FontWeight.SemiBold,modifier= Modifier.padding(top = 2.dp, start = 16.dp))
                    }


                    TextButton(onClick = {
                        addUserDetailsViewModel.add_detials(user_details_toSubmit)
                        profilePicture?.let {
                            addUserDetailsViewModel.add_user_pfp(it) //TODO IT KA USE KIYA HAI TO  PHLE .LET DKEHEGA KI NULL TO NAHI HAI AGAR NAHI HAI TO ANDAR KA IMPLEMENT HOGA AND IT SE YE HOGA KI USKI VALUE USKE ANDAR KE FUN KO MILJAYEGI AND VO NULL NAHI HOSKTA TO ERROR NAHI AAYEGA
                        }
                        loader = true
                    }, modifier = Modifier
                        .padding(top = 40.dp, start = 4.dp, end = 4.dp)
                        .clip(RoundedCornerShape(40.dp))
                        .fillMaxWidth()
                        .height(55.dp)
                        .background(Color.LightGray)) {
                        if (loader == true){
                            loaderAnimation()
                        }
                        else{
                        Text("Submit Details")
                        }

                    }

                }
            }
        }
    }
}

@Composable
fun loaderAnimation() {
    CircularProgressIndicator()
}


@Composable
fun get_the_extractor(Image:(File)-> Unit){
    imageExtractor(){
        recived->
        Image(recived)
    }
}
package com.cornstr.loggps.ui.screen

import android.util.Log
import android.widget.Button
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
//import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.cornstr.loggps.ui.ViewModels.APIViewModel
import com.cornstr.loggps.ui.ViewModels.HomeViewModel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.util.fastCbrt
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.cornstr.loggps.data.repository.refreshTokensRequest
import com.cornstr.loggps.data.repository.user_Data
import com.cornstr.loggps.ui.State.UiState
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.placeholder
import com.google.accompanist.placeholder.shimmer

@Composable
fun homeScreen(scaffoldPaddingValue : PaddingValues,navController: NavController){
    val homeViewModel: HomeViewModel = viewModel()
    val uistate by homeViewModel.uiState.collectAsState()
    var name by remember { mutableStateOf("") }
    var userData by remember { mutableStateOf(user_Data()) }
    var test by remember { mutableStateOf(false)
    }

    Log.d("54DTANIYAO","LV")

    if (test == false){
//        loading()
    }

    when(uistate){
        is UiState.Loading ->{
            test = true
        }
        is UiState.Success -> {
            val user_data = (uistate as UiState.Success).data
            name = user_data.message.toString()
            userData = user_data
            test = false
            Log.d("HOMESCREEN68","${user_data}")
        }
        is UiState.Error -> {Log.d("Home page 69","error")}
    }


    Column(modifier = Modifier
        .fillMaxSize()
        .padding()
        .background(Color.Black)
        ,horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
        Box(
            modifier = Modifier
                .fillMaxSize()
//                .padding(top = 100.dp)
        ) {
            Column(modifier = Modifier.padding(top = 30.dp)) {
                if (userData.name !=null) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(0.2f)
                            .padding(bottom = 4.dp)
                    ) {
                        Box(
                            modifier = Modifier
//                            .weight(0.4f)
                                .size(120.dp)
                                .padding(start = 2.dp)
                                .clip(CircleShape)
//                            .background(Color.White)
                                .border(width = 1.dp, color = Color.Gray, shape = CircleShape)
                                .placeholder(
                                    visible = test,
                                    highlight = PlaceholderHighlight.shimmer(Color.LightGray),
                                    color = Color.Gray
                                )

                        ) {
//                            Text("helo", modifier = Modifier.padding(start = 20.dp, top = 40.dp))
                            Image(painter = rememberAsyncImagePainter(userData.profile_Picture),
                                contentDescription = null,
                                modifier = Modifier.clip(CircleShape).matchParentSize())
                        }

                        Box(
                            modifier = Modifier
//                            .weight(0.6f)
                                .padding(vertical = 4.dp, horizontal = 5.dp)
                                .clip(RoundedCornerShape(10.dp))
//                            .background(Color.White)
                                .fillMaxSize()
                                .border(
                                    width = 2.dp,
                                    color = Color.Gray,
                                    shape = RoundedCornerShape(24.dp)
                                )
                                .placeholder(
                                    visible = test,
                                    highlight = PlaceholderHighlight.shimmer(Color.LightGray),
                                    color = Color.Gray
                                )
                        )
                        {
                            //TODO BOX KE ANDAR KA RAASTA
                            Column(
                                modifier = Modifier.fillMaxSize().padding(4.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {

                                Text(
                                    "${userData.name}",
                                    textAlign = TextAlign.Center,
                                    color = Color.White,
                                    fontWeight = FontWeight.SemiBold,
                                    modifier = Modifier.padding(top = 10.dp).weight(2f)
                                )
                                Divider(
                                    color = Color.DarkGray,
                                    modifier = Modifier.padding(horizontal = 10.dp)
                                )
                                Text(
                                    "${userData.contact_No}",
                                    textAlign = TextAlign.Center,
                                    color = Color.White,
                                    fontWeight = FontWeight.SemiBold,
                                    modifier = Modifier.padding(top = 10.dp).weight(2f)
                                )
                                Divider(
                                    color = Color.DarkGray,
                                    modifier = Modifier.padding(horizontal = 10.dp)
                                )
                                Text(
                                    "${userData.address}",
                                    textAlign = TextAlign.Center,
                                    color = Color.White,
                                    fontWeight = FontWeight.SemiBold,
                                    modifier = Modifier.padding(top = 10.dp).weight(2f)
                                )
                            }
                        }

                    }


                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(0.1f)
                            .padding(vertical = 4.dp, horizontal = 5.dp)
                            .clip(RoundedCornerShape(24.dp))
//                        .background(Color.White)
                            .border(
                                width = 1.dp,
                                color = Color.Gray,
                                shape = RoundedCornerShape(24.dp)
                            )
                            .placeholder(
                                visible = test,
                                highlight = PlaceholderHighlight.shimmer(Color.LightGray),
                                color = Color.Gray
                            )
                    ) {

                        Text(
                            "Gps Log Status $", style = TextStyle(
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White,
                                textAlign = TextAlign.Center
                            ), modifier = Modifier
                                .fillMaxSize()
                                .padding(top = 14.dp)
                        )
                    }
                } //    TODO END IF

                Box(
                    modifier = Modifier
                        .weight(0.3f)
                        .fillMaxWidth()
                        .padding(vertical = 4.dp, horizontal = 5.dp)
                        .border(width = 1.dp, color = Color.DarkGray, shape = RoundedCornerShape(24.dp))
                        .clip(RoundedCornerShape(24.dp))
//                        .background(Color.Gray)
//                        .background(Color.White)
                        .placeholder(
                            visible = test,
                            highlight = PlaceholderHighlight.shimmer(Color.LightGray),
                            color = Color.Gray
                        )
                )
                {
                    if (userData.message== "no-details"){
                        Column(modifier = Modifier.matchParentSize(),

                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center) {
                        Box(modifier = Modifier.padding(5.dp))
                            Text("You Have to Add your details in order to use our application ✅",
                                modifier = Modifier
                                    .padding(start = 8.dp, top = 24.dp, bottom = 16.dp),
                                fontWeight = FontWeight.ExtraBold
                                , color = Color.White,
                                textAlign = TextAlign.Center)

                            Button(onClick = {
                                navController.navigate("add_user_details_page")
                            }, modifier = Modifier.fillMaxWidth().size(50.dp).padding(horizontal = 8.dp)) {
                                Text("Add Details")
                            }
                        }
                    }
                    else{
                        Column(modifier = Modifier.matchParentSize().padding(horizontal = 4.dp)) {
                            Button(
                                onClick = {
                                    navController.navigate("add_company_details_page")
                                },
                                modifier = Modifier.fillMaxWidth()
                                    .weight(0.2f), shape = RoundedCornerShape(24.dp)
                            )
                            {
                                Text("Create Company")
                            }

                            Divider()

                            Button(
                                onClick = {},
                                modifier = Modifier.fillMaxWidth()
                                    .weight(0.2f), shape = RoundedCornerShape(24.dp)
                            )
                            {
                                Text("Join Company")
                            }
                        }
                    }
//                    T
                }


                if (userData.name != null){
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(0.5f)
                        .padding(vertical = 4.dp, horizontal = 5.dp)
                        .fillMaxHeight()
                        .clip(RoundedCornerShape(24.dp))
                        .border(width = 2.dp, color = Color.DarkGray, shape = RoundedCornerShape(24.dp))
//                        .background(Color.White)
                        .placeholder(
                            visible = test,
                            highlight = PlaceholderHighlight.shimmer(Color.LightGray),
                            color = Color.Gray
                        )
                )
                {
                    Column {
                        Box(
                            modifier = Modifier.fillMaxWidth().size(50.dp)
                                .clip(RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp))
                        ) {
                            Row(
                                modifier = Modifier
                                    .matchParentSize()
//                                    .background(Color.DarkGray)
                                    .clip(RoundedCornerShape(24.dp)),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center
                            )
                            {
                                Text(
                                    "Sno",
                                    color = Color.White,
                                    textAlign = TextAlign.Center,
                                    fontWeight = FontWeight.ExtraBold,
                                    modifier = Modifier.weight(1f)
                                )
                                Text(
                                    "Time",
                                    color = Color.White,
                                    textAlign = TextAlign.Center,
                                    fontWeight = FontWeight.ExtraBold,
                                    modifier = Modifier.weight(1f)
                                )
                                Text(
                                    "Status",
                                    color = Color.White,
                                    textAlign = TextAlign.Center,
                                    fontWeight = FontWeight.ExtraBold,
                                    modifier = Modifier.weight(1f)
                                )
                            }
                        }

                        Box(
                            modifier = Modifier.fillMaxWidth().weight(8f).size(50.dp)

                        ) {
                            Row(
                                modifier = Modifier
                                    .matchParentSize()
//                                    .background(Color.Gray)
                                    .clip(RoundedCornerShape(24.dp)),
                                verticalAlignment = Alignment.Bottom,
                                horizontalArrangement = Arrangement.Center
                            ) {}

                        }



                        Box(
                            modifier = Modifier.fillMaxWidth().weight(2f).size(50.dp)
                                .clip(RoundedCornerShape(bottomStart = 24.dp, bottomEnd = 24.dp))
                        ) {
                            TextButton(onClick = {}, modifier = Modifier.matchParentSize())
                            {
                                Text("See All Logs", fontWeight = FontWeight.Bold, fontSize = 20.sp, color = Color.Cyan)
                                Icon(Icons.Default.KeyboardArrowRight, contentDescription = null)
                            }

                        }


                    }
                }

            }//     TODO END IF

            }
        }
    }
}



package com.cornstr.loggps.ui.screen

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.room.util.TableInfo
import com.cornstr.loggps.data.repository.company_Create_Response
import com.cornstr.loggps.ui.ViewModels.AdminViewModel
import java.nio.file.WatchEvent
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import com.cornstr.loggps.data.repository.company_details
import com.cornstr.loggps.ui.State.Add_user_data_UiState
import com.cornstr.loggps.ui.State.Create_Company_UiState
import kotlin.math.max

@Composable
fun Admin_Dashboard(){
    var adminViewModel : AdminViewModel = viewModel()
    val uiState by adminViewModel.company_get.collectAsState()
    var details by remember { mutableStateOf(company_details())}


    when(uiState){
        is Create_Company_UiState.Loading -> {loading_animation()}
        is Create_Company_UiState.Error<*> -> {
            Log.d("ADMIN-SCREEN-47","CRASH")
        }
        is Create_Company_UiState.Success<*> -> {
            val response = (uiState as Create_Company_UiState.Success).data
            Log.d("ADMIN-SCREEN-56","$response")
            details = response
        }
    }




    Box(modifier = Modifier
        .clip(RoundedCornerShape(24.dp))
        .fillMaxSize()
        .padding(2.dp)
        .background(Color.DarkGray)
        .border(width = 1.dp, color = Color.Transparent, shape = RoundedCornerShape(24.dp))
    )
    {
        Column(modifier = Modifier.matchParentSize())
        {
            Box(modifier = Modifier
                .clip(RoundedCornerShape(24.dp))
                .fillMaxWidth()
                .background(Color.White)
                .weight(0.2f)
//                .height(150.dp)
            ){
                Row(modifier = Modifier.matchParentSize())
                {
                    Box(modifier = Modifier
                        .padding(top = 2.dp, start = 2.dp)
                        .clip(CircleShape)
                        .size(145.dp)
                        .background(Color.DarkGray),
                        contentAlignment = Alignment.Center
                    )
                    {
//                        TODO COMPANY LOGO HERE
                    }

                    Box(modifier = Modifier
                        .padding(top = 2.dp, start = 2.dp)
                        .clip(RoundedCornerShape(24.dp))
                        .size(width = 205.dp, height = 145.dp)
                        .background(Color.DarkGray),
                        contentAlignment = Alignment.Center
                    )
                    {
//                        TODO COMPANY MAIN DETAIL HERE
                        Image(painter = rememberAsyncImagePainter(details.logo), contentDescription = null,
                            modifier = Modifier.clip(CircleShape).matchParentSize())
                    }


                }
            }
            Divider()
            Box(modifier = Modifier
                .clip(RoundedCornerShape(24.dp))
                .fillMaxWidth()
                .background(Color.White)
                .weight(0.2f),
                contentAlignment = Alignment.Center
            )
            {
//                TODO COMPANY TECHNICAL DETAILS HERE
                Row(modifier = Modifier.matchParentSize()) {

                    Column(
                        modifier = Modifier
//                            .background(Color.LightGray)
                            .weight(0.4f)
                    )
                    {
                        Text("COMPANY GPS COORDINATES : ")
                        Text("Latitude : 1.2344")
                        Text("Longitude : 3.3456")
                        Text("Parameter : 30M")
                    }

                    Column(
                        modifier = Modifier
//                            .background(Color.Gray)
                            .weight(0.6f)
                    )
                    {
                        Text("COMPANY GPS COORDINATES : ")
                        Text("Latitude : 1.2344")
                        Text("Longitude : 3.3456")
                        Text("Parameter : 30M")
                    }
                }
            }
            Divider()
            Box(modifier = Modifier
                .clip(RoundedCornerShape(24.dp))
                .fillMaxWidth()
                .background(Color.White)
                .weight(0.6f)
            )
            {
//                TODO THIRD BOX
            }

        }
    }
}
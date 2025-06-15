package com.cornstr.loggps.ui.screen

import android.util.Log
import androidx.annotation.ColorRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ModalDrawer
import androidx.compose.material.icons.Icons
import com.cornstr.loggps.R
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import com.cornstr.loggps.ui.State.UiState
import com.cornstr.loggps.ui.ViewModels.APIViewModel
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.placeholder
import com.google.accompanist.placeholder.shimmer
import androidx.compose.material3.Icon
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource

@Composable
fun loading_Screen(viewModel: APIViewModel,navController : NavController){
    val uiState by viewModel.uiState.collectAsState()
    var show_auto_login_loading by remember { mutableStateOf(false) }

    LaunchedEffect(uiState) {
        Log.d("SXDE 31","UISTATE UP")

        when(uiState){
            is UiState.Error -> {
                Log.d("loading scree 31","IN")
                show_auto_login_loading = false
                Log.d("AXVPGADD scree 33","LOADING PAGE NXT")
                navController.navigate("login_page")
                {
                    Log.d("39MCLO","E")
                    navController.navigate("home_page"){
                        popUpTo("loading_screen"){inclusive = true}
                        Log.d("41AACC","D")
                    }
                }
            }
            UiState.Loading -> {show_auto_login_loading = true}

            is UiState.Success<*> -> {
                Log.d("AXVPGARR 45","ABOVE FALSE")
                show_auto_login_loading = false
                Log.d("AXVPGARR 47","ABOVE NAVIGATION")
                navController.navigate("home_page"){
                    popUpTo("loading_screen"){inclusive = true}
                }
            }
        }


    }
    Box(
        modifier = Modifier.fillMaxSize().background(Color.Transparent)
    ) {


        Box(
            modifier = Modifier
                .padding(0.dp)
                .size(300.dp)
                .clip(CircleShape)
//                .background(Color.Red)
                .align(Alignment.Center)

        ) {
            if (show_auto_login_loading){
                Box(modifier = Modifier.matchParentSize()
                    .placeholder(
                        visible = true,
                        color = Color.Transparent,
                        highlight = PlaceholderHighlight.shimmer(Color.White),
                    ))
            }
           Icon(painterResource(id = R.drawable.ic_profile), contentDescription = null, modifier = Modifier
               .size(300.dp)
               .align(Alignment.Center)
               )
            Log.d("SXDE 66", "UISTATE UP")
        }
    }

}
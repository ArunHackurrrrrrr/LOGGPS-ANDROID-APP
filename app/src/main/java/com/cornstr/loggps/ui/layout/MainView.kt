package com.cornstr.loggps.ui.layout

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.cornstr.loggps.R
import com.cornstr.loggps.ui.navigation.NavigateScreen
import com.cornstr.loggps.ui.navigation.Screen
import com.cornstr.loggps.ui.navigation.page_Screen

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainView(){

    val controller: NavController = rememberNavController()
    val navBackStackEntry by controller.currentBackStackEntryAsState()
    val currentScreen = navBackStackEntry?.destination?.route

    Scaffold(modifier = Modifier.fillMaxSize().padding(0.dp).background(Color.White),

//        topBar = { TopAppBar(title = { Text("$currentScreen")},
//            navigationIcon = {IconButton(onClick = {}) {
//                Icon(imageVector = Icons.Default.AccountBox, contentDescription = null)
//            }}) },
        bottomBar ={
            if (currentScreen == Screen.Pages.Home_Page.route){
                BottomNavigation(modifier = Modifier.wrapContentSize(), backgroundColor = Color.Black) {
                    page_Screen.forEach {
                        item ->
                        BottomNavigationItem(selected = currentScreen == item.route,
                            onClick = {},
                            icon = {Icon(painter = painterResource(id = item.icon), contentDescription = item.title)
                            },
                            label = {Text(text = item.title)})
                    }
                }
            }
        }
    ) {
        scaffPadding ->
        NavigateScreen(navController = controller, scaffoldPaddingValue = scaffPadding)
    }
}

package com.cornstr.loggps

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.cornstr.loggps.ui.layout.MainView
import com.cornstr.loggps.ui.screen.add_user_details
import com.cornstr.loggps.ui.theme.LOGGPSTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
        setContent {
            LOGGPSTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MainView()


                }
            }
        }
    }
}

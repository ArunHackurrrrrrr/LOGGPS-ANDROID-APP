package com.yourapp.utils

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import coil.compose.rememberAsyncImagePainter
import java.io.File



@Composable
fun imageExtractor(Image:(File)-> Unit){

    val context = LocalContext.current
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }
    val tempFile = File.createTempFile("pfp",".jpg",context.cacheDir)

    val launcher = rememberLauncherForActivityResult(    // THIS IS A LAUNCHER TO LAUNCH ANOTHER APP INSIDE OUR APP FOR EXAMPLE GALLERY
        contract = ActivityResultContracts.GetContent()  // THIS IS SAYING THAT HEY I WANT TO PICK SOME FILE / IMAGE / DOC / VIDEO ETC.
    ) {
        uri: Uri ? ->
        uri.let {
            selectedImageUri = it
        }
    }

    selectedImageUri?.let {
        uri ->
        var inputStream = context.contentResolver.openInputStream(uri)
        inputStream.use { // .USE WILL CLOSE THE STREAM AFTER GETTING OR POSTING THE DATA MAINTAINING MIN SYS MEMORY CONSUMPTION
            input ->
            tempFile.outputStream().use {   // EK OUTPUT STREAM JO KI EK TEMPORARY FILE HOGI AND VO ALREADY DEFINE HAI UPAR , AKA , TEMPORARY FILE KI EK STREAM BNAO
                output ->
                input?.copyTo(output)
                Image(tempFile)
            }
        }
    }

    TextButton(
        onClick = {launcher.launch("image/*")},
        modifier = Modifier
            .padding(top = 40.dp, start = 4.dp, end = 4.dp)
            .clip(RoundedCornerShape(40.dp))
            .fillMaxWidth()
            .height(55.dp)
            .background(Color.Cyan)
    ){
        Text("Upload From Gallery", fontSize = 20.sp, color = Color.Black)
    }


}
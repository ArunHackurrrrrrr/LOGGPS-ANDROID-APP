package com.cornstr.loggps.ui.screen

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Create
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.savedstate.savedState
import coil.compose.rememberAsyncImagePainter
import com.cornstr.loggps.data.repository.company_details
import com.cornstr.loggps.ui.State.Create_Company_UiState
import com.cornstr.loggps.ui.ViewModels.create_company_ViewModel
import com.yourapp.utils.imageExtractor
import java.io.File
import java.nio.file.WatchEvent

@Composable
fun company_Registration_Page(){
    var companyName : String by remember { mutableStateOf("") }
    var companyDescription : String by remember { mutableStateOf("") }
    var companyAddress : String by remember { mutableStateOf("") }
    var companyPhone : String by remember { mutableStateOf("") }
    var companyMail : String by remember { mutableStateOf("") }
    var companyLat : String by remember { mutableStateOf("") }
    var companyLong : String by remember { mutableStateOf("") }
    var companyParameter : String by remember { mutableStateOf("") }
    var companyRegNo : String by remember { mutableStateOf("") }
    var isActive : Boolean by remember { mutableStateOf(true) }
    var companyLogo by remember { mutableStateOf<File?>(null) }
    var get_Img by remember { mutableStateOf(false) }
    val createCompanyViewModel : create_company_ViewModel = viewModel()
    val uistate by createCompanyViewModel.company_create_response.collectAsState()
    var loading by remember { mutableStateOf(false) }

    val company_Details = company_details(
        name = companyName,
        descreption = companyDescription,
        address = companyAddress,
        company_phone = companyPhone,
        company_emial = companyMail,
        latitude = companyLat.toFloatOrNull(),
        longitude = companyLong.toFloatOrNull(),
        parameter = companyParameter.toIntOrNull(),
        is_active = isActive,
        company_Reg_Num = companyRegNo
    )



    when(uistate){
        is Create_Company_UiState.Error<*> -> TODO()
        Create_Company_UiState.Loading -> {}
        is Create_Company_UiState.Success<*> -> {Log.d("COMP-SCREEN-SUCESS-MSG","$")}
    }




    //TODO UI STARTS HERE

    Box(modifier = Modifier
        .background(Color.DarkGray)
        .fillMaxSize())
    {
        LazyColumn(modifier = Modifier
            .matchParentSize()
            .padding(8.dp)
            .clip(RoundedCornerShape(24.dp))
        )
        {
         item {
             Box(modifier = Modifier
                 .clip(RoundedCornerShape(24.dp))
//                 .background(Color.Red)
                 .fillMaxWidth()
                 .size(200.dp),
                 contentAlignment = Alignment.Center

             ){
                 Box(modifier = Modifier
                     .clip(CircleShape)
                     .size(190.dp)
                     .background(Color.White)

                 ){
                     companyLogo.let {
                         Image(painter = rememberAsyncImagePainter(model = it),
                             contentDescription = null, modifier = Modifier.clip(CircleShape).matchParentSize())
                     }
                 }

                 if (!get_Img){
                     IconButton(onClick = { get_Img = true}, modifier = Modifier
                         .clip(CircleShape)
                         .size(190.dp)
                         .background(Color.Cyan)

                     ) {
                         Icon(Icons.Default.Create,contentDescription = null, modifier = Modifier.size(30.dp))
                     }
                 }
                 if (get_Img){
                     get_Image() {
                         image->
                         companyLogo = image
                     }
                 }
             }
         }
        item {
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(24.dp))
//                    .background(Color.Blue)
                    .fillMaxWidth()
                    .height(65.dp),
                contentAlignment = Alignment.Center
            ){
                OutlinedTextField(
                    value = companyName,
                    onValueChange = { companyName = it }, // ✅ update the state
                    placeholder = { Text("Company Name" , color = Color.White) },       // ✅ placeholder must be a composable
                    modifier = Modifier
                        .clip(RoundedCornerShape(24.dp))
                        .fillMaxWidth()
                        .height(56.dp)
                        .background(Color.Black),
                    shape = RoundedCornerShape(24.dp),
                    textStyle = TextStyle(color = Color.White, lineHeight = 56.sp, fontSize = 20.sp)
                )
            }
        }



            item {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(24.dp))
//                    .background(Color.Blue)
                        .fillMaxWidth()
                        .height(65.dp),
                    contentAlignment = Alignment.Center
                ){
                    OutlinedTextField(
                        value = companyDescription,
                        onValueChange = { companyDescription = it }, // ✅ update the state
                        placeholder = { Text("Company Description" , color = Color.White) },       // ✅ placeholder must be a composable
                        modifier = Modifier
                            .clip(RoundedCornerShape(24.dp))
                            .fillMaxWidth()
                            .height(56.dp)
                            .background(Color.Black),
                        shape = RoundedCornerShape(24.dp),
                        textStyle = TextStyle(color = Color.White, lineHeight = 56.sp, fontSize = 20.sp)
                    )
                }
            }




            item {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(24.dp))
//                    .background(Color.Blue)
                        .fillMaxWidth()
                        .height(65.dp),
                    contentAlignment = Alignment.Center
                ){
                    OutlinedTextField(
                        value = companyAddress,
                        onValueChange = { companyAddress = it }, // ✅ update the state
                        placeholder = { Text("Company Address" , color = Color.White) },       // ✅ placeholder must be a composable
                        modifier = Modifier
                            .clip(RoundedCornerShape(24.dp))
                            .fillMaxWidth()
                            .height(56.dp)
                            .background(Color.Black),
                        shape = RoundedCornerShape(24.dp),
                        textStyle = TextStyle(color = Color.White, lineHeight = 56.sp, fontSize = 20.sp)
                    )
                }
            }

            item {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(24.dp))
//                    .background(Color.Blue)
                        .fillMaxWidth()
                        .height(65.dp),
                    contentAlignment = Alignment.Center
                ){
                    OutlinedTextField(
                        value = companyPhone,
                        onValueChange = { companyPhone = it }, // ✅ update the state
                        placeholder = { Text("Company Phone No." , color = Color.White) },       // ✅ placeholder must be a composable
                        modifier = Modifier
                            .clip(RoundedCornerShape(24.dp))
                            .fillMaxWidth()
                            .height(56.dp)
                            .background(Color.Black),
                        shape = RoundedCornerShape(24.dp),
                        textStyle = TextStyle(color = Color.White, lineHeight = 56.sp, fontSize = 20.sp)
                    )
                }
            }

            item {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(24.dp))
//                    .background(Color.Blue)
                        .fillMaxWidth()
                        .height(65.dp),
                    contentAlignment = Alignment.Center
                ){
                    OutlinedTextField(
                        value = companyMail,
                        onValueChange = { companyMail = it }, // ✅ update the state
                        placeholder = { Text("Company Email" , color = Color.White) },       // ✅ placeholder must be a composable
                        modifier = Modifier
                            .clip(RoundedCornerShape(24.dp))
                            .fillMaxWidth()
                            .height(56.dp)
                            .background(Color.Black),
                        shape = RoundedCornerShape(24.dp),
                        textStyle = TextStyle(color = Color.White, lineHeight = 56.sp, fontSize = 20.sp)
                    )
                }
            }


            item {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(24.dp))
//                    .background(Color.Blue)
                        .fillMaxWidth()
                        .height(65.dp),
                    contentAlignment = Alignment.Center
                ){
                    OutlinedTextField(
                        value = companyLat,
                        onValueChange = { companyLat = it }, // ✅ update the state
                        placeholder = { Text("Latitude" , color = Color.White) },       // ✅ placeholder must be a composable
                        modifier = Modifier
                            .clip(RoundedCornerShape(24.dp))
                            .fillMaxWidth()
                            .height(56.dp)
                            .background(Color.Black),
                        shape = RoundedCornerShape(24.dp),
                        textStyle = TextStyle(color = Color.White, lineHeight = 56.sp, fontSize = 20.sp)
                    )
                }
            }



            item {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(24.dp))
//                    .background(Color.Blue)
                        .fillMaxWidth()
                        .height(65.dp),
                    contentAlignment = Alignment.Center
                ){
                    OutlinedTextField(
                        value = companyLong,
                        onValueChange = { companyLong = it }, // ✅ update the state
                        placeholder = { Text("Longitude" , color = Color.White) },       // ✅ placeholder must be a composable
                        modifier = Modifier
                            .clip(RoundedCornerShape(24.dp))
                            .fillMaxWidth()
                            .height(56.dp)
                            .background(Color.Black),
                        shape = RoundedCornerShape(24.dp),
                        textStyle = TextStyle(color = Color.White, lineHeight = 56.sp, fontSize = 20.sp)
                    )
                }
            }



            item {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(24.dp))
//                    .background(Color.Blue)
                        .fillMaxWidth()
                        .height(65.dp),
                    contentAlignment = Alignment.Center
                ){
                    OutlinedTextField(
                        value = companyParameter,
                        onValueChange = { companyParameter = it }, // ✅ update the state
                        placeholder = { Text("Radius of Parameter" , color = Color.White) },       // ✅ placeholder must be a composable
                        modifier = Modifier
                            .clip(RoundedCornerShape(24.dp))
                            .fillMaxWidth()
                            .height(56.dp)
                            .background(Color.Black),
                        shape = RoundedCornerShape(24.dp),
                        textStyle = TextStyle(color = Color.White, lineHeight = 56.sp, fontSize = 20.sp)
                    )
                }
            }



            item {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(24.dp))
//                    .background(Color.Blue)
                        .fillMaxWidth()
                        .height(65.dp),
                    contentAlignment = Alignment.Center
                ){
                    OutlinedTextField(
                        value = companyRegNo,
                        onValueChange = { companyRegNo = it }, // ✅ update the state
                        placeholder = { Text("Company Registration NO." , color = Color.White) },       // ✅ placeholder must be a composable
                        modifier = Modifier
                            .clip(RoundedCornerShape(24.dp))
                            .fillMaxWidth()
                            .height(56.dp)
                            .background(Color.Black),
                        shape = RoundedCornerShape(24.dp),
                        textStyle = TextStyle(color = Color.White, lineHeight = 56.sp, fontSize = 20.sp)
                    )
                }
            }


            item {
                Button(
                    onClick = {
                        loading = true
                        companyLogo?.let {
                            try {
                                createCompanyViewModel.add_Company_Details(company_Details, it)
                            }catch (e: Exception){
                                Log.d("COMP-REG-SCREEN 380","$e")
                            }
                        }
                              },
                    modifier = Modifier.fillMaxWidth().size(60.dp).clip(RoundedCornerShape(24.dp)),
                    colors = ButtonDefaults.buttonColors(
                        Color.Cyan
                    )
                ) {
                    Text("Create Company !")
                    if (loading){
                        loaderAnimation()
                    }
                }
            }


        }

    }

}

@Composable
fun loading_animation(){
    CircularProgressIndicator()
}


@Composable
fun get_Image(image: (File)-> Unit){
    imageExtractor(true){
        recivedImg->
        image(recivedImg)

    }
}
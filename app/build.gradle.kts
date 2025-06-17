plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    id("kotlin-parcelize")
    id("kotlin-kapt")
}

android {
    namespace = "com.cornstr.loggps"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.cornstr.loggps"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {

    val nav_version = "2.7.5"
    val compose_version = "1.6.0-alpha08"
    val room = "2.6.0"

    //TODO PERMISSION DEPENDENCIES


    //TODO GPS LOATION ACCESS
    implementation("com.google.android.gms:play-services-maps:19.2.0")


    //TODO SKELETON LOADING ANIMATION
    implementation("com.google.accompanist:accompanist-placeholder-material:0.32.0")



    //TODO -> NAVIGATION
    implementation("androidx.navigation:navigation-compose:$nav_version")

    //TODO -> API CALL-OUT / INTERNET SERVICES USING RETROFIT
    implementation("com.squareup.retrofit2:retrofit:2.9.0")

    //TODO -> JSON TO KOTLIN CONVERTER BY USING RETROFIT WITH GSON CONVERTER
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")

    //TODO -> ROOM DATABSE KE LIYE HAI IMPORTS
    implementation("androidx.room:room-runtime:$room")
    implementation("androidx.room:room-ktx:$room")
    kapt("androidx.room:room-compiler:$room")

    //TODO -> IMAGE EXTRACTION AND SHOW
    implementation("io.coil-kt:coil-compose:2.5.0")

    //TODO LOGER TO SHOW WHAT IS FLOWING
    implementation("com.squareup.okhttp3:logging-interceptor:4.12.0")




    //RANDOM IMPORTANT THINGS FOR UI IG
    implementation("androidx.compose.ui:ui:$compose_version")
    implementation("androidx.compose.material:material:$compose_version")
    implementation("androidx.compose.ui:ui-tooling-preview:$compose_version")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.6.4")



    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}
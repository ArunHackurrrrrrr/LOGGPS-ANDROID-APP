package com.cornstr.loggps.data.local

import android.app.Application

class dbCreate : Application() {
    override fun onCreate() {
        super.onCreate()
        Graph.provideDao(this)
    }
}
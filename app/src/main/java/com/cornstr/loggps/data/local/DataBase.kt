package com.cornstr.loggps.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.cornstr.loggps.data.repository.JwtToken

@Database(
    entities = [JwtToken::class],
    version = 1,
    exportSchema = false
)
abstract class DataBase : RoomDatabase(){
    abstract fun tokenDao() : Dao
}
package com.cornstr.loggps.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import androidx.room.Upsert
import com.cornstr.loggps.data.repository.JwtToken


@Dao
abstract class Dao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun addTokens(tokens : JwtToken)

    @Query("select * from `token-table`")
    abstract suspend fun getTokens() : JwtToken

    @Update
    abstract suspend fun updateTokens(tokens: JwtToken)

    @Delete
    abstract suspend fun deleteTokens(tokens: JwtToken)
}
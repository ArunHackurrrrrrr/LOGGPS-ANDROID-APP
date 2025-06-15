package com.cornstr.loggps.data.repository

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "token-table")
data class JwtToken(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    @ColumnInfo("access")
    val access_Token : String = "",
    @ColumnInfo("refresh")
    val refresh_Token : String = ""
)
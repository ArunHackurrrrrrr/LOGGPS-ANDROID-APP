package com.cornstr.loggps.data.repository

import com.cornstr.loggps.data.local.Dao

class localRepository(private val Dao: Dao) {



    //TODO FUNCTION TO ADD NEW TOKENS
    suspend fun addTokens(tokens : JwtToken){
        Dao.addTokens(tokens = tokens)
    }

    //TODO FUNCTION TO GET STORED TOKENS
    suspend fun getTokens(): JwtToken = Dao.getTokens()

    //TODO FUNCTION TO UPDATE THE OLD TOKENS WITH NEW ONE
    suspend fun updateTokens(tokens: JwtToken){
        Dao.updateTokens(tokens = tokens)
    }

    //TODO FUNCTION TO DELETE THE TOKENS
    suspend fun deleteTokens(tokens: JwtToken){
        Dao.deleteTokens(tokens = tokens)
    }


}
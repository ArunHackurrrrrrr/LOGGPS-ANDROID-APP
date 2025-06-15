package com.cornstr.loggps.data.local

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cornstr.loggps.data.repository.localRepository
import kotlinx.coroutines.launch

class DaoFun (
    private val Daofunctions : localRepository = Graph.Dao
) : ViewModel(){

    lateinit var get_Tokens : String

    init {
        viewModelScope.launch {
            suspend { get_Tokens = Daofunctions.getTokens().toString()}
        }
    }
}
package com.cornstr.loggps.data.local

import android.content.Context
import androidx.room.Room
import com.cornstr.loggps.data.repository.localRepository
import com.cornstr.loggps.data.repository.remoteRepository

object Graph {
    lateinit var dataBase: DataBase

    val Dao by lazy {
        localRepository(dataBase.tokenDao())
    }

    fun provideDao(context: Context){
        dataBase = Room.databaseBuilder(context = context, DataBase::class.java,"token-table").build()
    }
}



//DEKHO DIMAG KAFI KHARAB HUA PAR YE EASY HAI , JAB BHI APP LAUNCH HOGA TO JO dbCreate karke file bnai hai na vo onCreatee ki wjh se chalegi and upar jo funcion hai provideDao isko run karegi with passing contxt jisse database ka ek instance build hoga and jisko we can use from as we defined in Dao dekho jo instance bnkar milga usme already jo dao diya hai vo utni cmd bani milgi and then dekho fir upar wale fun me val Dao hai and usko diya hai localRepo(dataBase.tokenDao), jisse local Repo me Dao pahuch ra hai jisse sari vha ki cmd chal rahi hai , hopee karta hu ki samagh jaoge agar future me bhool gaye to baki asan hai yaar!!
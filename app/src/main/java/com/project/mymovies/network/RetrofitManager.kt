package com.project.mymovies.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitManager {

    var retrofitService: APIInterface? = null
    const val API_KEY = "c50f5aa4e7c95a2a553d29b81aad6dd0"

    fun getInstance(): APIInterface {

        if (retrofitService == null) {
            val retrofit = Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/3/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            retrofitService = retrofit.create(APIInterface::class.java)
        }
        return retrofitService!!
    }
}
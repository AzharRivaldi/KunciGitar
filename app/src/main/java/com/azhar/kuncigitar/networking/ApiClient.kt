package com.azhar.kuncigitar.networking

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by Azhar Rivaldi on 12-04-2021
 * Youtube Channel : https://bit.ly/2PJMowZ
 * Github : https://github.com/AzharRivaldi
 * Twitter : https://twitter.com/azharrvldi_
 * Instagram : https://www.instagram.com/azhardvls_
 * Linkedin : https://www.linkedin.com/in/azhar-rivaldi
 */

object ApiClient {

    fun getApiClient(): Retrofit {
        return Retrofit.Builder()
                .baseUrl("https://hadi-api.herokuapp.com/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
    }

}
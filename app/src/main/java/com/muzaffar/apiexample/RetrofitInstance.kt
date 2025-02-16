package com.muzaffar.apiexample

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

// THe Singleton object that is reponsible to connect to the API
// THe different function, eg: getPosts will use this object to call the API
object RetrofitInstance {
    private const val BASE_URL = "https://jsonplaceholder.typicode.com/"
    val apiService:ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()) // Transform JSON to Object Post
            .build()
            .create(ApiService::class.java)
    }
}
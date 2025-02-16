package com.muzaffar.apiexample

import retrofit2.http.GET

interface ApiService {
    @GET("posts")
    suspend fun getPosts():List<Post>

}
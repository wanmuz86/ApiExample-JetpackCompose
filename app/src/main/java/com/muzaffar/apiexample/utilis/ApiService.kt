package com.muzaffar.apiexample.utilis

import com.muzaffar.apiexample.model.Post
import retrofit2.http.GET

interface ApiService {
    @GET("posts")
    suspend fun getPosts():List<Post>

}
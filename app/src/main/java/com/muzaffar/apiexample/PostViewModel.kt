package com.muzaffar.apiexample

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

// ViewModel to fetch the posts from the API [MVVM]
class PostViewModel : ViewModel(){
    // LiveData to hold the list of posts
    private val _posts = MutableLiveData<List<Post>>(emptyList())
    val posts: LiveData<List<Post>> = _posts

    // State to hold the loading state
    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> = _isLoading

    // State to hold the error state
    private val _error = mutableStateOf<String?>(null)
    val error:State<String?> = _error

    fun fetchPosts(){
        // Launch a coroutine (perform in background) an API call to fetch the posts
        viewModelScope.launch {
            _isLoading.value = true
            try {
                // Fetch the posts from the API
                // Use the singleton object to call the API
                _posts.value = RetrofitInstance.apiService.getPosts()
              _error.value = null
            }catch (e:Exception){
                // Handle the error in case error with API call
                _error.value = "Failed to load data ${e.message}"
                Log.e("PostViewModel", "Error: $e")
            }
            finally {
                _isLoading.value = false
            }
        }
    }

}
package com.muzaffar.apiexample.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.muzaffar.apiexample.ui.theme.APIExampleTheme
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import com.muzaffar.apiexample.viewmodel.PostViewModel
import com.muzaffar.apiexample.model.Post

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            APIExampleTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        TopAppBar(title = { Text(text = "API Example") })
                    }
                ) {
                    padding->
                    PostListScreen(modifier=Modifier.padding(padding))
                }

            }
        }
    }
}

@Composable
fun PostItem(post: Post){
    Card(
        modifier = Modifier.fillMaxWidth().padding(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ){
        Column(
            modifier = Modifier.padding(16.dp)
        ){
            Text(text = post.title, fontWeight = FontWeight.Bold, fontSize = 18.sp)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = post.body, fontSize = 14.sp, color = Color.Gray)
        }
    }
}

@Composable
fun PostListScreen(modifier:Modifier, viewModel: PostViewModel = viewModel()){
    val posts by viewModel.posts.observeAsState(emptyList())
    val isLoading by viewModel.isLoading
    val error by viewModel.error

    // When the screen is loaded, fetch the posts
    LaunchedEffect(Unit) {
        // Call the API to fetch the posts
        viewModel.fetchPosts() //If there is a change in the ViewModel, the composable will recompose
    }
    Box(modifier= modifier.fillMaxSize().padding()){
            when {
                // If the page is loading, show circular progress indicator
                isLoading -> {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
                // If there is an error, show the error message
                error != null -> {
                    Text(
                        text = error!!,
                        color = Color.Red,
                        modifier = Modifier.align(Alignment.Center)
                    )
            }
                else -> {
                    LazyColumn (
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(8.dp)
                    ){
                        // For each post, show a PostItem composable
                        items(posts){
                            post -> PostItem(post)
                        }
                    }
                }
        }
    }
}
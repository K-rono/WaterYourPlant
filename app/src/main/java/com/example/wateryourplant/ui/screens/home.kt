package com.example.wateryourplant.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewModelScope
import com.example.wateryourplant.ui.viewmodel.HomeViewModel
import com.example.wateryourplant.util.showNotification
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun Home(
    viewModel: HomeViewModel
) {
    TweetScreen(viewModel = viewModel)
}

@Composable
fun TweetScreen(viewModel: HomeViewModel) {
    val context = LocalContext.current
    var tweetText by remember { mutableStateOf("") }
    var result by remember { mutableStateOf<String?>(null) }
    val coroutineScope = viewModel.viewModelScope

    Column(modifier = Modifier.padding(16.dp)) {
        TextField(
            value = tweetText,
            onValueChange = { tweetText = it },
            label = { Text("This is an automated tweet!") }
        )
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = {
            //viewModel.sendTweet(tweetText)
            //viewModel.sendXTweet(tweetText)
            coroutineScope.launch(Dispatchers.IO) {
                if(viewModel.sendTelegram(tweetText)) {
                    showNotification(
                        context,
                        "Water your plant!",
                        "Look who's ignoring their plants again >:("
                    )
                }
            }
        }) {
            Text("Tweet")
        }
    }
}




package com.example.wateryourplant.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.wateryourplant.R
import kotlinx.coroutines.delay

@Composable
fun FlowerAnimation(
    emotion: String
){
    when (emotion) {
        "happy" -> FlowerDefaultAnimation()
        else -> FlowerDefaultAnimation()
    }
}

@Composable
private fun FlowerDefaultAnimation(modifier: Modifier = Modifier){
    val frames = listOf(
        R.drawable.flower_1,
        R.drawable.flower_2,
        R.drawable.flower_3
    )

    val currentFrame by produceState(initialValue = 0) {
        while (true) {
            delay(500L) // speed of animation 200ms
            value = (value + 1) % frames.size
        }
    }

    Box(
        modifier = modifier
            .size(300.dp) // Adjust size accordingly
            .background(Color.Transparent), // Transparent background
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = frames[currentFrame]),
            contentDescription = "Potato Flying Animation",
            modifier = Modifier.fillMaxSize()
        )
    }
}
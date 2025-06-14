package com.example.wateryourplant.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.example.wateryourplant.R
import kotlinx.coroutines.delay

@Composable
fun FlowerAnimation(
    emotion: String
){
    when (emotion) {
        "Happy" -> FlowerDefaultAnimation()
        "Cold" -> FlowerColdAnimation()
        "Hot" -> FlowerHotAnimation()
        "Dry" -> FlowerDryAnimation()
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

@Composable
private fun FlowerColdAnimation(modifier: Modifier = Modifier){
    val frames = listOf(
        R.drawable.cold1,
        R.drawable.cold1,
        R.drawable.cold1
    )
    val offset by produceState(0) {
        while (true){
            delay(50L)
            value = (0..15).random()
        }
    }
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
            modifier = Modifier.size(230.dp).offset{ IntOffset(offset,0) }
        )
    }
}

@Composable
private fun FlowerHotAnimation(modifier: Modifier = Modifier){
    val frames = listOf(
        R.drawable.hot1,
        R.drawable.hot2,
    )

    val offset by produceState(0) {
        while (true){
            delay(100L)
            value = (0..15).random()
        }
    }
    val currentFrame by produceState(initialValue = 0) {
        while (true) {
            delay(1000L) // speed of animation 200ms
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
            modifier = Modifier.size(230.dp).offset{ IntOffset(if(currentFrame == 1) offset else 0,0) }
        )
    }
}

@Composable
private fun FlowerDryAnimation(modifier: Modifier = Modifier){
    val frames = listOf(
        R.drawable.dry1,
        R.drawable.dry2,
    )
    val offset by produceState(0) {
        while (true){
            delay(50L)
            value = (0..10).random()
        }
    }

    val offset2 by produceState(0) {
        while (true){
            delay(20L)
            value = (0..40).random()
        }
    }

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
            modifier = Modifier.size(230.dp).offset{ IntOffset(if(currentFrame==1)offset else offset2, 0) }
        )
    }
}
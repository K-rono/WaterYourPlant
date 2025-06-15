package com.example.wateryourplant.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.tooling.preview.Preview
import com.example.wateryourplant.data.repository.RaspPiRepository

@Composable
fun PlantCareApp() {
    val plantUiState = RaspPiRepository.sensorDataFlow.collectAsState()
    val isMoist = plantUiState.value.moisture
    val temperatureLevel = plantUiState.value.temperatureLevel

    val emotion =
        if (plantUiState.value.moisture.equals("Dry")) "Dry"
        else if (plantUiState.value.temperatureLevel > 33f) "Hot"
        else if (plantUiState.value.temperatureLevel < 28f) "Cold"
        else "Happy"


    HomeScreen(
        isMoist = isMoist,
        tempLevel = temperatureLevel,
        emotion = emotion,
        nickname = "Eric the Plant"
    )
}

@Preview(showBackground = true)
@Composable
fun PlantCareAppPreview() {
    PlantCareApp()
}
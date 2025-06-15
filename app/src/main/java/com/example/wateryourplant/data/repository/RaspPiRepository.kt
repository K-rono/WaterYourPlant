package com.example.wateryourplant.data.repository

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

object RaspPiRepository {
    private val _sensorDataFlow = MutableStateFlow(PlantUiState("None", 0f))
    val sensorDataFlow = _sensorDataFlow.asStateFlow()

    fun updateData(moisture: String, temp: Float) {
        _sensorDataFlow.value = PlantUiState(moisture, temp)
    }
}

data class PlantUiState(
    val moisture: String,
    val temperatureLevel: Float,
)
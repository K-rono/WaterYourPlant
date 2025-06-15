package com.example.wateryourplant.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.wateryourplant.data.repository.RaspPiRepository
import com.example.wateryourplant.ui.components.DialogBox
import com.example.wateryourplant.ui.viewmodel.HomeViewModel

@Composable
fun PlantCareApp() {
    val plantUiState = RaspPiRepository.sensorDataFlow.collectAsState()
    val isMoist = plantUiState.value.moisture
    val temperatureLevel = plantUiState.value.temperatureLevel

    val config = LocalConfiguration.current
    val screenWidth = config.screenWidthDp.dp
    val screenHeight = config.screenHeightDp.dp
    val homeViewModel: HomeViewModel = viewModel()
    val homeUiState by homeViewModel.homeUiState.collectAsState()

    val emotion =
        if (plantUiState.value.moisture == "Dry") "Dry"
        else if (plantUiState.value.temperatureLevel > 33f) "Hot"
        else if (plantUiState.value.temperatureLevel < 28f) "Cold"
        else "Happy"

    Scaffold() {
        innerPadding ->
        Box(modifier = Modifier.padding(innerPadding))
        HomeScreen(
            isMoist = isMoist,
            tempLevel = temperatureLevel,
            emotion = emotion,
            nickname = "Eric the Plant"
        )
    }

    DialogBox(
        screenWidth = screenWidth,
        screenHeight = screenHeight,
        dialogMessage = homeUiState.dialogMessage,
        isDialogVisible = homeUiState.isDialogVisible,
        onDismiss = { homeViewModel.onDialogDismissed() }
    )
}

@Preview(showBackground = true)
@Composable
fun PlantCareAppPreview() {
    PlantCareApp()
}
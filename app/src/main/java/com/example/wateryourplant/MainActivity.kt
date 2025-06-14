package com.example.wateryourplant

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.wateryourplant.ui.screens.PlantCareApp
import com.example.wateryourplant.ui.theme.WaterYourPlantTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            WaterYourPlantTheme {
                //WaterYourPlant()
                PlantCareApp()
            }
        }
    }
}

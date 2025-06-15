package com.example.wateryourplant

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Intent

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.content.ContextCompat
import com.example.wateryourplant.service.PlantMonitorService
import com.example.wateryourplant.ui.screens.PlantCareApp
import com.example.wateryourplant.ui.theme.WaterYourPlantTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        createNotificationChannel()

        //Plant monitoring foreground service
        val serviceIntent = Intent(this, PlantMonitorService::class.java)
        ContextCompat.startForegroundService(this, serviceIntent)

        enableEdgeToEdge()
        setContent {
            WaterYourPlantTheme {
                PlantCareApp()
            }
        }
    }

    private fun createNotificationChannel() {
        val importance = NotificationManager.IMPORTANCE_HIGH
        val channel =
            NotificationChannel("message_channel", "Message Notification", importance).apply {
                description =
                    "Sends a notification when a message is triggered due to changing sensor readings from the plant"
            }
        // register the channel
        val notificationManager = ContextCompat.getSystemService(
            this,
            NotificationManager::class.java
        ) as NotificationManager

        notificationManager.createNotificationChannel(channel)
    }
}


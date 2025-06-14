package com.example.wateryourplant.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import com.example.wateryourplant.data.repository.RaspPiRepository
import com.example.wateryourplant.util.createNotification
import com.example.wateryourplant.util.showNotification
import java.util.Timer
import java.util.TimerTask

class PlantMonitorService : Service() {

    private val timer = Timer()

    override fun onCreate() {
        super.onCreate()
        startForeground(1, createNotification(this,"Water Your Plant","Monitoring Started"))

        timer.schedule(object : TimerTask() {
            override fun run() {
                checkSensorData()
            }
        }, 0, 1_000) // every 15 seconds
    }

    private fun checkSensorData() {
        val moisture = (0..1).random() != 0 // Replace with actual API result
        val temp = (5..40).random().toFloat()

        RaspPiRepository.updateData(moisture, temp)

        if (moisture) {
            showNotification(this, "Plant Alert", "Moisture too low!")
        }
        // Use Retrofit to call Flask API
        // If temperature/humidity meet conditions:
        // - Send Telegram message
        // - Show Notification
        // - Save to Room DB or file
        //showNotification(this, "Water Your Plant","New Noti")
    }

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onDestroy() {
        super.onDestroy()
        timer.cancel()
    }

}

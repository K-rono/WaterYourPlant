package com.example.wateryourplant.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import com.example.wateryourplant.data.network.RaspPiLocalService
import com.example.wateryourplant.data.repository.RaspPiRepository
import com.example.wateryourplant.util.createNotification
import com.example.wateryourplant.util.showNotification
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Timer
import java.util.TimerTask

class PlantMonitorService : Service() {

    private val timer = Timer()
    private var lastDryNoti: Long = 0L
    private val notificationIntervalMillis = 1 * 60 * 1000
    private var isSecondTime = false
    private var isRedditPosted = false

    private var isFirstTimeHot = true
    private var isFirstTimeCold = true
    private var lastHotNoti: Long = 0L
    private var lastColdNoti: Long = 0L
    private var lastComfyNoti: Long = 0L

    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    override fun onCreate() {
        super.onCreate()
        startForeground(1, createNotification(this, "Water Your Plant", "Monitoring Started"))

        timer.schedule(object : TimerTask() {
            override fun run() {
                coroutineScope.launch {
                    checkSensorData()
                }
            }
        }, 0, 5_000) // every 15 seconds
    }

    private suspend fun checkSensorData() {
//        val moisture =
//            (0..1).random().let { if (it == 1) "Moist" else "Dry" }// Replace with actual API result
//        val temp = (5..40).random().toFloat()

        var moisture = "Dry"
        var temperature = 0f

        try {
            val response = RaspPiLocalService.api.getReadings()
            if (response.isSuccessful) {
                val reading = response.body()
                Log.i("readings", reading.toString())
                if (reading != null) {
                    moisture = reading.Moisture
                    temperature = reading.Temperature

                    RaspPiRepository.updateData(moisture, temperature)
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        val currentTime = System.currentTimeMillis()

        if (moisture == "Dry" && currentTime - lastDryNoti > notificationIntervalMillis && !isRedditPosted) {
            val title = if (!isSecondTime) "Hey, You." else "I've warned you!"
            val message =
                if (!isSecondTime) "You are neglecting your plant! >:(" else "You shall face judgement by the SubReddit now!"
            showNotification(this, title, message)

            if (isSecondTime) {
                PostingService.sendTelegram("Could you please give me some water mate - by your beloved and dried up plant.")
                //PostingService.sendReddit("")
                isRedditPosted = true
                lastDryNoti = System.currentTimeMillis()

            } else {
                isSecondTime = true
            }

        } else if (moisture == "Moist" && lastDryNoti > 0) {
            showNotification(this, "AHH, SWEET H20", "I almost forgot how water taste like.")
            lastDryNoti = 0L
            isSecondTime = false
            isRedditPosted = false
        }

        if (temperature > 33f && isFirstTimeHot) {
            showNotification(
                this,
                "Mate, do you think I'm hot?",
                "Yeah, DO YOU SEE THE TEMPS? I'M GETTING COOKED ALIVE"
            )
            PostingService.sendTelegram("Can someone please turn on the air conditioner?")
            lastHotNoti = System.currentTimeMillis()
            isFirstTimeHot = false
            lastComfyNoti = 0L
        } else if (temperature < 28f && isFirstTimeCold) {
            showNotification(
                this,
                "I'm as cold as your crushes' eyes when you got rejected",
                "Seriously, are we living in the north pole?"
            )
            PostingService.sendTelegram("Can someone please turn OFF the air conditioner?")
            lastColdNoti = System.currentTimeMillis()
            isFirstTimeCold = false
            lastComfyNoti = 0L
        } else if (temperature in 28f..33f && lastComfyNoti == 0L && (lastColdNoti > 0 || lastHotNoti >0)) {
            showNotification(
                this,
                "Ah good heavens",
                "Ain't the weather beautiful today mate, I'm feeling extra comfy now"
            )
            lastComfyNoti = System.currentTimeMillis()
            lastHotNoti = 0L
            lastColdNoti = 0L
            isFirstTimeHot = true
            isFirstTimeCold = true
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

data class SensorReading(
    val Moisture: String,
    val Temperature: Float
)


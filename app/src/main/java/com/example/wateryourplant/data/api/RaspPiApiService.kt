package com.example.wateryourplant.data.api

import com.example.wateryourplant.service.SensorReading
import retrofit2.Response
import retrofit2.http.GET

interface RaspPiApiService {
    @GET("status")
    suspend fun getReadings(): Response<SensorReading>
}
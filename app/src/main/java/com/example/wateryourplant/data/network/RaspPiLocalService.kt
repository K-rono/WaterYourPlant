package com.example.wateryourplant.data.network

import com.example.wateryourplant.data.api.RaspPiApiService

object RaspPiLocalService {
    private const val RASP_PI_BASE_URL = "http://192.168.0.135:5000/"

    val api: RaspPiApiService by lazy {
        RetrofitFactory.create(RASP_PI_BASE_URL).create(RaspPiApiService::class.java)
    }
}
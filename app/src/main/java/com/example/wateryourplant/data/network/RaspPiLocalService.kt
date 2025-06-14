package com.example.wateryourplant.data.network

import com.example.wateryourplant.data.api.RaspPiApiService

object RaspPiLocalService {
    private const val RASP_PI_BASE_URL = "http://172.20.10.3:5000/"

    val api: RaspPiApiService by lazy {
        RetrofitFactory.create(RASP_PI_BASE_URL).create(RaspPiApiService::class.java)
    }
}
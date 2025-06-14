package com.example.wateryourplant.data.network

import com.example.wateryourplant.data.api.TelegramApiService

object TelegramService {
    private const val TELEGRAM_BASE_URL = "https://api.telegram.org/bot7769968470:AAEy7cWXMju5sON8SZxcwS1qfPBB0pqWLdc/"

    val api: TelegramApiService by lazy {
        RetrofitFactory.create(TELEGRAM_BASE_URL).create(TelegramApiService::class.java)
    }
}

package com.example.wateryourplant.data.api

import retrofit2.http.*
import retrofit2.Response

interface TelegramApiService {
    @GET("sendMessage")
    suspend fun sendMessage(
        @Query("chat_id") chatId: String,
        @Query("text") message: String
    ): Response<Unit>
}


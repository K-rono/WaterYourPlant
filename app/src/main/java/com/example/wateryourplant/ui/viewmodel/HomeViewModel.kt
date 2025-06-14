package com.example.wateryourplant.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Credentials
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import org.json.JSONObject
import java.io.IOException

class HomeViewModel : ViewModel() {
    fun sendTweet(status: String) {
        val client = OkHttpClient()

        val requestBody = FormBody.Builder()
            .add("grant_type", "password")
            .add("username", "traalalelotralala")
            .add("password", "limqihong_567")
            .build()

        val credential = Credentials.basic("co1yvtBcexIAB6j9rbrJFw", "wQc0b0mzMZy6na2hviEFJD041vuw6A")

        val request = Request.Builder()
            .url("https://www.reddit.com/api/v1/access_token")
            .post(requestBody)
            .header("Authorization", credential)
            .header("User-Agent", "android:com.example.wateryourplant:v1.0 (by /u/traalalelotralala)")
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.e("RedditAuth", "Failed: ${e.message}")
            }

            override fun onResponse(call: Call, response: Response) {
                response.use {
                    if (!it.isSuccessful) {
                        Log.e("RedditAuth", "Error: ${it.code} - ${it.body?.string()}")
                        return
                    }

                    val responseBody = it.body?.string()
                    val json = JSONObject(responseBody ?: "")
                    val accessToken = json.getString("access_token")
                    Log.d("RedditAuth", "Access token: $accessToken")

                    val postBody = FormBody.Builder()
                        .add("sr", "kotlinmemes") // Subreddit name
                        .add("title", "Hello from Kotlin!")
                        .add("kind", "self")
                        .add("text", "This is a test post from my app.")
                        .build()

                    val postRequest = Request.Builder()
                        .url("https://oauth.reddit.com/api/submit")
                        .post(postBody)
                        .addHeader("Authorization", "bearer $accessToken")
                        .addHeader("User-Agent", "android:com.yourapp.name:v1.0 (by /u/traalalelotralala)")
                        .build()

                    client.newCall(postRequest).enqueue(object : Callback {
                        override fun onFailure(call: Call, e: IOException) {
                            Log.e("RedditPost", "Post failed: ${e.message}")
                        }

                        override fun onResponse(call: Call, response: Response) {
                            Log.d("RedditPost", "Post response: ${response.body?.string()}")
                        }
                    })

                }
            }
        })

    }
}
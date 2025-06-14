package com.example.wateryourplant.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.wateryourplant.data.network.TelegramService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
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
    fun sendReddit(status: String) {
        val client = OkHttpClient()

        val requestBody = FormBody.Builder()
            .add("grant_type", "password")
            .add("username", "traalalelotralala")
            .add("password", "limqihong_567")
            .build()

        val credential =
            Credentials.basic("co1yvtBcexIAB6j9rbrJFw", "wQc0b0mzMZy6na2hviEFJD041vuw6A")

        val request = Request.Builder()
            .url("https://www.reddit.com/api/v1/access_token")
            .post(requestBody)
            .header("Authorization", credential)
            .header(
                "User-Agent",
                "android:com.example.wateryourplant:v1.0 (by /u/traalalelotralala)"
            )
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
                        .addHeader(
                            "User-Agent",
                            "android:com.yourapp.name:v1.0 (by /u/traalalelotralala)"
                        )
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

    suspend fun sendTelegram(status: String): Boolean {
        return try {
            val response = TelegramService.api.sendMessage(
                chatId = "1249305211",
                message = "Hi from Retrofit!"
            )
            if (response.isSuccessful) {
                Log.d("Telegram", "Message sent successfully")
                true
            } else {
                Log.e("Telegram", "Failed: ${response.errorBody()?.string()}")
                false
            }
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }


    fun sendXTweet(status: String) {
//        val client = OkHttpClient()
//
//        val json = """
//{
//  "text": "Hello from v2 API!"
//}
//""".trimIndent()
//
//        val request = Request.Builder()
//            .url("https://api.twitter.com/2/tweets")
//            .addHeader("Authorization", "Bearer AAAAAAAAAAAAAAAAAAAAAOLU2QEAAAAA31moIY0prnDfNMoHF2VneDbC5II%3D8nNdGU1ZE99eZPkhGKK91d7HYtOb89EyxLdbRAMHxJOCwCgwI0")
//            .addHeader("Content-Type", "application/json")
//            .post(json.toRequestBody("application/json".toMediaType()))
//            .build()
//
//        client.newCall(request).enqueue(object : Callback {
//            override fun onFailure(call: Call, e: IOException) {
//                Log.e("TwitterAPI", "Failed to post tweet", e)
//            }
//
//            override fun onResponse(call: Call, response: Response) {
//                Log.d("TwitterAPI", "Tweet response: ${response.code} - ${response.body?.string()}")
//            }
//        })

//        val cb = ConfigurationBuilder()
//        cb.setDebugEnabled(true)
//            .setOAuthConsumerKey("WsOBDymJ6w3lm9IBatphi4Slg")
//            .setOAuthConsumerSecret("q4mNzIroq4pP4jHKeRwozHaPFUtHUGlNw1CxjDKvqHaucUJ6pk")
//            .setOAuthAccessToken("1815245870537027584-r2kFvS4uc0E3tsnYvQBy2TMgtkFrDq")
//            .setOAuthAccessTokenSecret("m1sLqaCN8lREC6Aw3cS5WsUrxQ2BtImEbI5mJT6ecKzae")
//
//        val tf = TwitterFactory(cb.build())
//        val twitter = tf.instance
//        viewModelScope.launch(Dispatchers.IO) {
//           try{
//               twitter.updateStatus(status)
//           } catch (e: TwitterException){
//                e.printStackTrace()
//           }
//        }
    }
}


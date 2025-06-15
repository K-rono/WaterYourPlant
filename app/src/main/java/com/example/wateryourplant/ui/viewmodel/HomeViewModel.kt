package com.example.wateryourplant.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wateryourplant.data.network.TelegramService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
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
    private val _homeUiState = MutableStateFlow(HomeUiState())
    val homeUiState = _homeUiState.asStateFlow()

    init {
        displayResponse("Hi")
    }

    // ------ Pet response ---------
    private fun displayResponse(message: String) {
        viewModelScope.launch {
            if (_homeUiState.value.isDialogVisible) {
                while (_homeUiState.value.isDialogVisible) {
                    delay(100)
                }
            }
            withContext(Dispatchers.Main) {
                _homeUiState.update {
                    it.copy(
                        dialogMessage = message,
                        isDialogVisible = true
                    )
                }
            }
//            delay(5000)
            withContext(Dispatchers.Main) {
                _homeUiState.update { it.copy(isDialogVisible = false) }
            }
        }
    }

    fun onDialogDismissed() {
        viewModelScope.launch {
            _homeUiState.update { it.copy(isDialogVisible = false) }
        }
    }
}


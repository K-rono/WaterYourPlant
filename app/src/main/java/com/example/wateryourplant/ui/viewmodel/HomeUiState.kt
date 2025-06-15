package com.example.wateryourplant.ui.viewmodel

data class HomeUiState (
    val dialogMessage: String = "",
    val isDialogVisible: Boolean = false,
    val isDialogDismissed: Boolean = false,
)
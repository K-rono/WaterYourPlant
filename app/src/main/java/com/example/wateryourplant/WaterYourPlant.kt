package com.example.wateryourplant

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.wateryourplant.ui.screens.Home
import com.example.wateryourplant.ui.viewmodel.HomeViewModel

enum class Screens {
    HOME
}

@Composable
fun WaterYourPlant(
    navController: NavHostController = rememberNavController()
) {
    val homeViewModel: HomeViewModel = viewModel()

    Scaffold { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            NavHost(navController = navController, startDestination = Screens.HOME.name) {
                composable(
                    route = Screens.HOME.name
                ) {
                    Home(viewModel = homeViewModel)
                }
            }
        }
    }
}

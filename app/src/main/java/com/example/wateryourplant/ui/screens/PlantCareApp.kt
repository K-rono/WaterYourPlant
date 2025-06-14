package com.example.wateryourplant.ui.screens

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController

// Enum for navigation states
enum class Screen {
    HOME,
    HISTORY
}

@Composable
fun PlantCareApp(
    navController: NavHostController = rememberNavController(),
) {
    val config = LocalConfiguration.current
    val screenWidth = config.screenWidthDp.dp
    val screenHeight = config.screenHeightDp.dp

    // State to manage current screen
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    // Mock data for demonstration
    var plantNickname by remember { mutableStateOf("My Lovely Plant") }
    var plantEmotion by remember { mutableStateOf("Happy") } // Happy, Thirsty, Hot, Cold, Healthy
    var moistureLevel by remember { mutableStateOf(65) } // Percentage
    var temperatureLevel by remember { mutableStateOf(24.5f) } // Celsius

    Scaffold(
        bottomBar = {
            NavigationBar(
                containerColor = MaterialTheme.colorScheme.surface
            ) {
                val items = listOf(
                    Screen.HOME to Icons.Default.Home,
                    Screen.HISTORY to Icons.Default.Email
                )

                items.forEach { (screen, icon) ->
                    NavigationBarItem(
                        selected = currentRoute == screen.name,
                        onClick = {
                            if (currentRoute != screen.name) {
                                navController.navigate(screen.name) {
                                    popUpTo(Screen.HOME.name) { inclusive = false }
                                    launchSingleTop = true
                                }
                            }
                        },
                        icon = {
                            Icon(
                                imageVector = icon,
                                contentDescription = screen.name
                            )
                        },
                        label = {
                            Text(screen.name.lowercase().replaceFirstChar { it.uppercase() })
                        },
                        alwaysShowLabel = true,
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = MaterialTheme.colorScheme.primary,
                            unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    )
                }
            }
        }
    ) { innerPadding ->
        Box {
            NavHost(
                navController = navController,
                startDestination = Screen.HOME.name,
                enterTransition = {
                    slideIntoContainer(
                        AnimatedContentTransitionScope.SlideDirection.Right,
                        tween(700)
                    )
                },
                exitTransition = {
                    slideOutOfContainer(
                        AnimatedContentTransitionScope.SlideDirection.Right,
                        tween(700)
                    )
                },
                popEnterTransition = {
                    slideIntoContainer(
                        AnimatedContentTransitionScope.SlideDirection.Right,
                        tween(700)
                    )
                },
                popExitTransition = {
                    slideOutOfContainer(
                        AnimatedContentTransitionScope.SlideDirection.Right,
                        tween(700)
                    )
                },
                modifier = Modifier.padding(innerPadding)
            ) {
                composable(route = Screen.HOME.name) {
                    HomeScreen(
                        moistureLevel = moistureLevel,
                        temperatureLevel = temperatureLevel,
                        onHistoryClick = { navController.navigate(Screen.HISTORY.name) }
                    )
                }

                composable(route = Screen.HISTORY.name) {
                    WaterReminderHistoryScreen(
                        onBackClick = { navController.navigate(Screen.HOME.name) }
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PlantCareAppPreview() {
    PlantCareApp()
}
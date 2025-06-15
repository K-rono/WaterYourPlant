package com.example.wateryourplant.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.wateryourplant.R
import com.example.wateryourplant.ui.components.FlowerAnimation
import com.example.wateryourplant.ui.components.DialogBox
import com.example.wateryourplant.ui.viewmodel.HomeViewModel

@SuppressLint("DefaultLocale")
@Composable
fun HomeScreen(
    isMoist: String,
    tempLevel: Float,
    emotion: String,
    nickname: String
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween,
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.65f)
                    .padding(top = 20.dp, bottom = 20.dp),
                shape = RoundedCornerShape(12.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                colors = CardDefaults.cardColors(containerColor = Color.Black)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Placeholder for plant animation
                    Spacer(modifier = Modifier.height(55.dp))
                    FlowerAnimation(emotion = emotion)
                    Image(
                        painter = painterResource(id = R.drawable.flower),
                        contentDescription = "Plant Animation",
                        modifier = Modifier.size(250.dp, 80.dp)
                    )
                    Text(
                        text = nickname,
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )
                }
            }

            // Moisture and Temperature Levels (side-by-side)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.3f)
                    .padding(bottom = 20.dp),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.Bottom
            ) {
                // Moisture Level Card
                Card(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .padding(end = 8.dp),
                    shape = RoundedCornerShape(12.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFE6CFFF).copy(alpha = 0.7f))
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier.size(100.dp)
                        ) {
                            GaugeProgressIndicator(
                                progress = if (isMoist == "Moist") 0f else 100f,
                                color = Color(0xFFBBDEFB),
                                trackColor = Color(0xFF2196F3),
                                modifier = Modifier.fillMaxSize()
                            )
                            Icon(
                                imageVector = Icons.Default.AddCircle,
                                contentDescription = "Moisture",
                                tint = Color(0xFFBBDEFB),
                                modifier = Modifier.size(40.dp)
                            )
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Moisture Level",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            text = if (isMoist == "Moist") "Moist" else "Dry",
                            fontSize = 28.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFFBBDEFB)
                        )
                    }
                }

                val minTemp = 5f
                val maxTemp = 40f
                val clampedTemp = tempLevel.coerceIn(minTemp, maxTemp)
                val tempProgress = (clampedTemp - minTemp) / (maxTemp - minTemp)

                // Temperature Level Card
                Card(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .padding(start = 8.dp),
                    shape = RoundedCornerShape(12.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFE6CFFF).copy(alpha = 0.7f))
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier.size(100.dp)
                        ) {
                            GaugeProgressIndicator(
                                progress = tempProgress,
                                color = Color(0xFFF44336),
                                trackColor = Color(0xFFFFC4C4),
                                modifier = Modifier.fillMaxSize()
                            )
                            Icon(
                                imageVector = Icons.Default.Info,
                                contentDescription = "Temperature",
                                tint = Color(0xFFF44336),
                                modifier = Modifier.size(48.dp)
                            )
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Temperature Level",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            text = String.format("%.1f°C", tempLevel),
                            fontSize = 28.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFFFFC4C4)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun GaugeProgressIndicator(
    progress: Float,
    color: Color,
    trackColor: Color,
    modifier: Modifier = Modifier,
    strokeWidth: Dp = 10.dp
) {
    Canvas(modifier = modifier) {
        val sweepAngle = 270f * progress // Full sweep angle for semi-circle effect
        val startAngle = 135f // Start from bottom-left
        val stroke = Stroke(width = strokeWidth.toPx(), cap = StrokeCap.Round)

        // Draw track (background arc)
        drawArc(
            color = trackColor,
            startAngle = startAngle,
            sweepAngle = 270f, // Full 270 degrees for the track
            useCenter = false,
            style = stroke
        )

        // Draw progress (foreground arc)
        drawArc(
            color = color,
            startAngle = startAngle,
            sweepAngle = sweepAngle,
            useCenter = false,
            style = stroke
        )
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    HomeScreen(
        isMoist = "Moist",
        tempLevel = 24.5f,
        emotion = "Cold",
        nickname = "Eric the Plant"
    )
}
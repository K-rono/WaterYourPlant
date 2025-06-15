package com.example.wateryourplant.ui.components

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.example.wateryourplant.R

@Composable
fun DialogBox(
    screenWidth: Dp,
    screenHeight: Dp,
    dialogMessage: String,
    isDialogVisible: Boolean,
    onDismiss: () -> Unit,
) {
    Log.i("triggered","triggered")
    val transitionState = remember { MutableTransitionState(true) }

    LaunchedEffect(isDialogVisible) {
        transitionState.targetState = isDialogVisible
    }

    if (transitionState.targetState || !transitionState.isIdle) {
        AnimatedVisibility(
            visibleState = transitionState,
            enter = slideInVertically(
                animationSpec = tween(durationMillis = 1000, easing = FastOutSlowInEasing),
                initialOffsetY = { it - 50 } // Slide in from the bottom
            ) + fadeIn(),
            exit = slideOutVertically(
                animationSpec = tween(durationMillis = 500, easing = FastOutSlowInEasing),
                targetOffsetY = { -(it / 6) } // Slide out to the bottom
            )
        ) {
            Box(
                modifier = Modifier
                    .offset(x = screenWidth * 0.04F, y = screenHeight * 0.05F)
                    .size(width = screenWidth * 0.92F, height = screenHeight * 0.1F)
                    .shadow(elevation = 32.dp, shape = RoundedCornerShape(20.dp))
                    .background(color = Color.White.copy(alpha = 0.9f))
                    .border(
                        border = BorderStroke(2.dp, Color.LightGray),
                        shape = RoundedCornerShape(20.dp)
                    )
                    .zIndex(2F)
                    .clickable {
                        if (isDialogVisible) {
                            onDismiss()
                        }
                    }
            ) {
                Text(
                    text = dialogMessage,
                    fontFamily = FontFamily(Font(R.font.pokemon_dp_pro)),
                    fontWeight = FontWeight.W500,
                    lineHeight = 35.sp,
                    fontSize = 32.sp,
                    modifier = Modifier.padding(horizontal = 15.dp, vertical = 10.dp)
                )
            }
        }
    }
}
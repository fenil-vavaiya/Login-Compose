package com.example.loginscreencompose

import android.annotation.SuppressLint
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.hazeChild

@SuppressLint("UseOfNonLambdaOffsetOverload")
@Composable
fun GlassCard(
    modifier: Modifier, hazeState: HazeState
) {
    Box(modifier = modifier
        .fillMaxWidth()
        // No offset applied, card doesn't move on click
        .hazeChild(state = hazeState, shape = RoundedCornerShape(12.dp))
        .border(
            width = Dp.Hairline, brush = Brush.verticalGradient(
                colors = listOf(
                    Color.White.copy(alpha = .8f),
                    Color.White.copy(alpha = .2f),
                ),
            ), shape = RoundedCornerShape(12.dp)
        )) {

    }
}
package com.example.loginscreencompose.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.loginscreencompose.R

// Set of Material typography styles to start with
val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    )
)

val Jost = FontFamily(
    Font(R.font.jost_bold, FontWeight.Bold),
    Font(R.font.jost_semibold, FontWeight.SemiBold),
    Font(R.font.jost_medium, FontWeight.Medium),
    Font(R.font.jost_regular, FontWeight.Normal),
    Font(R.font.jost_light, FontWeight.Light),
)

val AppTypography = Typography(
    bodyLarge = TextStyle(
        fontFamily = Jost, fontSize = 16.sp
    ),
    headlineLarge = TextStyle(
        fontFamily = Jost, fontSize = 24.sp, fontWeight = FontWeight.Bold, platformStyle = PlatformTextStyle(includeFontPadding = false)
    ),
)



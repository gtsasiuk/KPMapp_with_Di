package com.example.kpmapp_with_di.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.sp


fun getTypography(
    titleFont: Font,
    bodyFont: Font
): Typography {

    val titleFamily = FontFamily(titleFont)
    val bodyFamily = FontFamily(bodyFont)

    return Typography(

        displayLarge = TextStyle(
            fontFamily = titleFamily,
            fontSize = 40.sp
        ),

        displayMedium = TextStyle(
            fontFamily = titleFamily,
            fontSize = 34.sp
        ),

        headlineLarge = TextStyle(
            fontFamily = titleFamily,
            fontSize = 32.sp
        ),

        headlineMedium = TextStyle(
            fontFamily = titleFamily,
            fontSize = 26.sp
        ),

        titleLarge = TextStyle(
            fontFamily = titleFamily,
            fontSize = 22.sp
        ),

        titleMedium = TextStyle(
            fontFamily = titleFamily,
            fontSize = 18.sp
        ),

        bodyLarge = TextStyle(
            fontFamily = bodyFamily,
            fontSize = 18.sp
        ),

        bodyMedium = TextStyle(
            fontFamily = bodyFamily,
            fontSize = 16.sp
        ),

        bodySmall = TextStyle(
            fontFamily = bodyFamily,
            fontSize = 14.sp
        ),

        labelLarge = TextStyle(
            fontFamily = bodyFamily,
            fontSize = 16.sp
        ),

        labelMedium = TextStyle(
            fontFamily = bodyFamily,
            fontSize = 14.sp
        ),

        labelSmall = TextStyle(
            fontFamily = bodyFamily,
            fontSize = 12.sp
        )
    )
}

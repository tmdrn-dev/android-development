package com.example.mediaplayer.common.ui.theme

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

val ButtonText =
    TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 26.sp,
        lineHeight = 35.62.sp,
    )

val ContentTitle =
    TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.SemiBold,
        fontSize = 32.sp,
        lineHeight = 43.84.sp,
        color = AppViewTitle,
    )

val SelectedTabTitle =
    TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Bold,
        fontSize = 28.sp,
        lineHeight = 35.62.sp,
        color = AppViewTabBarSelectedText,
    )

val NormalTabTitle =
    TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 26.sp,
        lineHeight = 35.62.sp,
        color = AppViewTabBarNormalText,
    )

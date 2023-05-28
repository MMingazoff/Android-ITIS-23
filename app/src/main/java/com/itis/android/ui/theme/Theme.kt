package com.itis.android.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowCompat

@Composable
fun Theme(
    style: Style = Style.Purple,
    textSize: Size = Size.Medium,
    paddingSize: Size = Size.Medium,
    corners: Corners = Corners.Rounded,
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = when {
        darkTheme -> {
            when (style) {
                Style.Purple -> purpleDarkPalette
                Style.Blue -> blueDarkPalette
                Style.Orange -> orangeDarkPalette
                Style.Red -> redDarkPalette
                Style.Green -> greenDarkPalette
            }
        }

        else -> {
            when (style) {
                Style.Purple -> purpleLightPalette
                Style.Blue -> blueLightPalette
                Style.Orange -> orangeLightPalette
                Style.Red -> redLightPalette
                Style.Green -> greenLightPalette
            }
        }
    }

    val typography = Typography(
        heading = TextStyle(
            fontSize = when (textSize) {
                Size.Small -> 24.sp
                Size.Medium -> 28.sp
                Size.Big -> 32.sp
            },
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily.Monospace,
        ),
        body = TextStyle(
            fontSize = when (textSize) {
                Size.Small -> 14.sp
                Size.Medium -> 16.sp
                Size.Big -> 18.sp
            },
            fontWeight = FontWeight.Normal
        ),
        toolbar = TextStyle(
            fontFamily = FontFamily.Monospace,
            fontSize = when (textSize) {
                Size.Small -> 14.sp
                Size.Medium -> 16.sp
                Size.Big -> 18.sp
            },
            fontWeight = FontWeight.Medium
        ),
        caption = TextStyle(
            fontSize = when (textSize) {
                Size.Small -> 10.sp
                Size.Medium -> 12.sp
                Size.Big -> 14.sp
            }
        )
    )

    val shapes = Shape(
        padding = when (paddingSize) {
            Size.Small -> 12.dp
            Size.Medium -> 16.dp
            Size.Big -> 20.dp
        },
        cornersStyle = when (corners) {
            Corners.Flat -> RoundedCornerShape(0.dp)
            Corners.Rounded -> RoundedCornerShape(8.dp)
        }
    )

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colors.tintColor.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme
        }
    }

    CompositionLocalProvider(
        LocalColors provides colors,
        LocalTypography provides typography,
        LocalShape provides shapes,
        content = content
    )
}

package com.itis.android.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.graphics.Shape as AndroidShape

data class Colors(
    val primaryText: Color,
    val primaryBackground: Color,
    val secondaryText: Color,
    val secondaryBackground: Color,
    val tintColor: Color,
    val controlColor: Color,
    val errorColor: Color,
)

data class Typography(
    val heading: TextStyle,
    val body: TextStyle,
    val toolbar: TextStyle,
    val caption: TextStyle
)

data class Shape(
    val padding: Dp,
    val cornersStyle: AndroidShape
)

data class Images(
    val id: Int,
    val contentDesc: String
)

object Theme {

    val colors: Colors
        @Composable
        get() = LocalColors.current

    val typography: Typography
        @Composable
        get() = LocalTypography.current

    val shapes: Shape
        @Composable
        get() = LocalShape.current
}

enum class Style {
    Purple, Orange, Blue, Red, Green
}

enum class Size {
    Small, Medium, Big
}

enum class Corners {
    Flat, Rounded
}

val LocalColors = staticCompositionLocalOf<Colors> {
    error("No colors provided")
}

val LocalTypography = staticCompositionLocalOf<Typography> {
    error("No font provided")
}

val LocalShape = staticCompositionLocalOf<Shape> {
    error("No shapes provided")
}

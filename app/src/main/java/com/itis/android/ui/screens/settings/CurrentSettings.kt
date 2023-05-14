package com.itis.android.ui.screens.settings

import com.itis.android.ui.theme.Corners
import com.itis.android.ui.theme.Size
import com.itis.android.ui.theme.Style

data class CurrentSettings(
    val isDarkMode: Boolean,
    val textSize: Size,
    val paddingSize: Size,
    val cornerStyle: Corners,
    val style: Style,
)

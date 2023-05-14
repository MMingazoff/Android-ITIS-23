package com.itis.android.ui.screens.settings

import androidx.compose.runtime.staticCompositionLocalOf
import com.itis.android.ui.theme.Corners
import com.itis.android.ui.theme.Size
import com.itis.android.ui.theme.Style
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class SettingsEventBus {

    private val _currentSettings: MutableStateFlow<CurrentSettings> = MutableStateFlow(
        CurrentSettings(
            isDarkMode = false,
            cornerStyle = Corners.Rounded,
            style = Style.Orange,
            textSize = Size.Medium,
            paddingSize = Size.Medium
        )
    )
    val currentSettings: StateFlow<CurrentSettings> = _currentSettings

    fun updateDarkMode(isDarkMode: Boolean) {
        _currentSettings.value = _currentSettings.value.copy(isDarkMode = isDarkMode)
    }

    fun updateCornerStyle(corners: Corners) {
        _currentSettings.value = _currentSettings.value.copy(cornerStyle = corners)
    }

    fun updateStyle(style: Style) {
        _currentSettings.value = _currentSettings.value.copy(style = style)
    }
}

val LocalSettingsEventBus = staticCompositionLocalOf {
    SettingsEventBus()
}

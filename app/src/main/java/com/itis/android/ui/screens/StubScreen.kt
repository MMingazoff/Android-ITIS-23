package com.itis.android.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.itis.android.ui.theme.Theme

@Composable
fun StubScreen() {
    Surface(color = Theme.colors.primaryBackground) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
        ) {
            Text(
                text = "Stub :)",
                modifier = Modifier.align(Alignment.Center),
                color = Theme.colors.primaryText,
            )
        }
    }
}

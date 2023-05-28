package com.itis.android.ui.screens.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Card
import androidx.compose.material.Checkbox
import androidx.compose.material.CheckboxDefaults
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.TopAppBar
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.itis.android.R
import com.itis.android.ui.theme.Corners
import com.itis.android.ui.theme.Style
import com.itis.android.ui.theme.Theme
import com.itis.android.ui.theme.blueDarkPalette
import com.itis.android.ui.theme.blueLightPalette
import com.itis.android.ui.theme.orangeDarkPalette
import com.itis.android.ui.theme.orangeLightPalette
import com.itis.android.ui.theme.purpleDarkPalette
import com.itis.android.ui.theme.purpleLightPalette

@Composable
fun SettingsScreen(
    navController: NavController
) {
    val settingsEventBus = LocalSettingsEventBus.current
    val currentSettings = settingsEventBus.currentSettings.collectAsState().value

    Surface(color = Theme.colors.primaryBackground) {
        Column(modifier = Modifier.fillMaxSize()) {
            TopAppBar(
                backgroundColor = Theme.colors.primaryBackground,
                elevation = 8.dp
            ) {
                Text(
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = Theme.shapes.padding),
                    text = stringResource(id = R.string.settings),
                    color = Theme.colors.primaryText,
                    style = Theme.typography.toolbar
                )
            }

            Row(
                modifier = Modifier.padding(Theme.shapes.padding),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier.weight(1f),
                    text = "Dark Theme",
                    color = Theme.colors.primaryText,
                    style = Theme.typography.body
                )
                Checkbox(
                    checked = currentSettings.isDarkMode, onCheckedChange = {
                        settingsEventBus.updateDarkMode(!currentSettings.isDarkMode)
                    },
                    colors = CheckboxDefaults.colors(
                        checkedColor = Theme.colors.tintColor,
                        uncheckedColor = Theme.colors.secondaryText
                    )
                )
            }

            Divider(
                modifier = Modifier.padding(start = Theme.shapes.padding),
                thickness = 0.5.dp,
                color = Theme.colors.secondaryText.copy(
                    alpha = 0.3f
                )
            )

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                elevation = 8.dp,
                backgroundColor = Theme.colors.secondaryBackground,
                shape = Theme.shapes.cornersStyle
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = "Shape type", color = Theme.colors.secondaryText)
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Card(
                            modifier = Modifier.weight(1f),
                            backgroundColor = Theme.colors.primaryBackground
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    modifier = Modifier
                                        .weight(1f)
                                        .padding(horizontal = 8.dp),
                                    text = "Round",
                                    color = Theme.colors.primaryText,
                                    style = Theme.typography.body
                                )
                                Checkbox(
                                    checked = currentSettings.cornerStyle == Corners.Rounded,
                                    onCheckedChange = {
                                        settingsEventBus.updateCornerStyle(Corners.Rounded)
                                    },
                                    colors = CheckboxDefaults.colors(
                                        checkedColor = Theme.colors.tintColor,
                                        uncheckedColor = Theme.colors.secondaryText
                                    )
                                )
                            }
                        }

                        Spacer(modifier = Modifier.width(8.dp))

                        Card(
                            modifier = Modifier.weight(1f),
                            backgroundColor = Theme.colors.primaryBackground
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    modifier = Modifier
                                        .weight(1f)
                                        .padding(horizontal = 8.dp),
                                    text = "Flat",
                                    color = Theme.colors.primaryText,
                                    style = Theme.typography.body
                                )
                                Checkbox(
                                    checked = currentSettings.cornerStyle == Corners.Flat,
                                    onCheckedChange = {
                                        settingsEventBus.updateCornerStyle(Corners.Flat)
                                    },
                                    colors = CheckboxDefaults.colors(
                                        checkedColor = Theme.colors.tintColor,
                                        uncheckedColor = Theme.colors.secondaryText
                                    )
                                )
                            }
                        }
                    }
                }
            }

            Divider(
                modifier = Modifier.padding(start = Theme.shapes.padding),
                thickness = 0.5.dp,
                color = Theme.colors.secondaryText.copy(
                    alpha = 0.3f
                )
            )

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                elevation = 8.dp,
                backgroundColor = Theme.colors.secondaryBackground,
                shape = Theme.shapes.cornersStyle
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = "Tint color", color = Theme.colors.secondaryText)

                    Row(
                        modifier = Modifier
                            .padding(Theme.shapes.padding)
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        ColorCard(
                            color = if (currentSettings.isDarkMode)
                                purpleDarkPalette.tintColor
                            else
                                purpleLightPalette.tintColor,
                            onClick = {
                                settingsEventBus.updateStyle(Style.Purple)
                            })
                        ColorCard(
                            color = if (currentSettings.isDarkMode)
                                orangeDarkPalette.tintColor
                            else
                                orangeLightPalette.tintColor,
                            onClick = {
                                settingsEventBus.updateStyle(Style.Orange)
                            })
                        ColorCard(
                            color = if (currentSettings.isDarkMode)
                                blueDarkPalette.tintColor
                            else
                                blueLightPalette.tintColor,
                            onClick = {
                                settingsEventBus.updateStyle(Style.Blue)
                            })
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun ColorCard(color: Color, onClick: () -> Unit) {
    Card(
        onClick = { onClick() },
        modifier = Modifier
            .size(56.dp, 56.dp),
        backgroundColor = color,
        shape = Theme.shapes.cornersStyle,
        elevation = 3.dp,
    ) {}
}

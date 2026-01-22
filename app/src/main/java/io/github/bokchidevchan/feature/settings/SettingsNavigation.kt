package io.github.bokchidevchan.feature.settings

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable
data object SettingsRoute

fun NavGraphBuilder.settingsScreen(
    onNavigateBack: () -> Unit
) {
    composable<SettingsRoute> {
        SettingsScreen(onNavigateBack = onNavigateBack)
    }
}

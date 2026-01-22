package io.github.bokchidevchan.feature.profile

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import kotlinx.serialization.Serializable

@Serializable
data class ProfileRoute(val userId: Long = 1L)

fun NavGraphBuilder.profileScreen(
    onNavigateBack: () -> Unit,
    onNavigateToSettings: () -> Unit
) {
    composable<ProfileRoute> {
        ProfileScreen(
            onNavigateBack = onNavigateBack,
            onNavigateToSettings = onNavigateToSettings
        )
    }
}

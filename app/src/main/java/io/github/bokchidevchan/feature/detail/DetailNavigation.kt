package io.github.bokchidevchan.feature.detail

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import io.github.bokchidevchan.core.navigation.Route

fun NavGraphBuilder.detailScreen(
    onNavigateBack: () -> Unit
) {
    composable<Route.Detail> {
        DetailScreen(onNavigateBack = onNavigateBack)
    }
}

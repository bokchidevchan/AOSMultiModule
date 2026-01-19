package io.github.bokchidevchan.feature.home

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import io.github.bokchidevchan.core.navigation.Route

fun NavGraphBuilder.homeScreen(
    onNavigateToDetail: (itemId: Int, title: String) -> Unit
) {
    composable<Route.Home> {
        HomeScreen(onNavigateToDetail = onNavigateToDetail)
    }
}

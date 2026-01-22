package io.github.bokchidevchan.feature.search

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import io.github.bokchidevchan.core.navigation.Route
import kotlinx.serialization.Serializable

@Serializable
data object SearchRoute

fun NavGraphBuilder.searchScreen(
    onArticleClick: (Long) -> Unit,
    onUserClick: (Long) -> Unit
) {
    composable<SearchRoute> {
        SearchScreen(
            onArticleClick = onArticleClick,
            onUserClick = onUserClick
        )
    }
}

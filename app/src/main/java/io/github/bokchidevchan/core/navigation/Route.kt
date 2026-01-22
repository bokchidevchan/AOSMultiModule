package io.github.bokchidevchan.core.navigation

import kotlinx.serialization.Serializable

/** Type-safe navigation route 정의 */
sealed interface Route {

    @Serializable
    data object Home : Route

    @Serializable
    data class Detail(
        val itemId: Int,
        val title: String
    ) : Route
}

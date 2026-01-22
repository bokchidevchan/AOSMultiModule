package io.github.bokchidevchan.core.model

import kotlinx.serialization.Serializable

@Serializable
data class Settings(
    val darkMode: Boolean = false,
    val notificationsEnabled: Boolean = true,
    val language: String = "ko",
    val fontSize: FontSize = FontSize.MEDIUM,
    val autoPlayVideos: Boolean = true
)

@Serializable
enum class FontSize {
    SMALL, MEDIUM, LARGE, EXTRA_LARGE
}

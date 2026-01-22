package io.github.bokchidevchan.core.domain.usecase

import io.github.bokchidevchan.core.domain.repository.SettingsRepository
import io.github.bokchidevchan.core.model.Settings
import javax.inject.Inject

class UpdateSettingsUseCase @Inject constructor(
    private val settingsRepository: SettingsRepository
) {
    suspend operator fun invoke(settings: Settings) {
        settingsRepository.updateSettings(settings)
    }

    suspend fun updateDarkMode(enabled: Boolean) {
        settingsRepository.updateDarkMode(enabled)
    }

    suspend fun updateNotifications(enabled: Boolean) {
        settingsRepository.updateNotifications(enabled)
    }

    suspend fun updateLanguage(language: String) {
        settingsRepository.updateLanguage(language)
    }
}

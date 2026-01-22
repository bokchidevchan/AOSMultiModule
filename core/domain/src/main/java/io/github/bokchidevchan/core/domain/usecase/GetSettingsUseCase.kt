package io.github.bokchidevchan.core.domain.usecase

import io.github.bokchidevchan.core.domain.repository.SettingsRepository
import io.github.bokchidevchan.core.model.Settings
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetSettingsUseCase @Inject constructor(
    private val settingsRepository: SettingsRepository
) {
    operator fun invoke(): Flow<Settings> {
        return settingsRepository.getSettings()
    }
}

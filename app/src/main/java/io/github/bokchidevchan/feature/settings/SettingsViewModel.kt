package io.github.bokchidevchan.feature.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.bokchidevchan.core.domain.usecase.GetSettingsUseCase
import io.github.bokchidevchan.core.domain.usecase.UpdateSettingsUseCase
import io.github.bokchidevchan.core.model.FontSize
import io.github.bokchidevchan.core.model.Settings
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val getSettingsUseCase: GetSettingsUseCase,
    private val updateSettingsUseCase: UpdateSettingsUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(SettingsUiState())
    val uiState: StateFlow<SettingsUiState> = _uiState.asStateFlow()

    init {
        loadSettings()
    }

    private fun loadSettings() {
        viewModelScope.launch {
            getSettingsUseCase().collect { settings ->
                _uiState.update {
                    it.copy(
                        settings = settings,
                        isLoading = false
                    )
                }
            }
        }
    }

    fun updateDarkMode(enabled: Boolean) {
        viewModelScope.launch {
            updateSettingsUseCase.updateDarkMode(enabled)
        }
    }

    fun updateNotifications(enabled: Boolean) {
        viewModelScope.launch {
            updateSettingsUseCase.updateNotifications(enabled)
        }
    }

    fun updateLanguage(language: String) {
        viewModelScope.launch {
            updateSettingsUseCase.updateLanguage(language)
        }
    }

    fun updateFontSize(fontSize: FontSize) {
        val currentSettings = _uiState.value.settings
        viewModelScope.launch {
            updateSettingsUseCase(currentSettings.copy(fontSize = fontSize))
        }
    }

    fun updateAutoPlayVideos(enabled: Boolean) {
        val currentSettings = _uiState.value.settings
        viewModelScope.launch {
            updateSettingsUseCase(currentSettings.copy(autoPlayVideos = enabled))
        }
    }
}

data class SettingsUiState(
    val settings: Settings = Settings(),
    val isLoading: Boolean = true
)

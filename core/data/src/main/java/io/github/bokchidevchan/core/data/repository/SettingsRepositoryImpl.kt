package io.github.bokchidevchan.core.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import io.github.bokchidevchan.core.domain.repository.SettingsRepository
import io.github.bokchidevchan.core.model.FontSize
import io.github.bokchidevchan.core.model.Settings
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SettingsRepositoryImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : SettingsRepository {

    private object PreferenceKeys {
        val DARK_MODE = booleanPreferencesKey("dark_mode")
        val NOTIFICATIONS = booleanPreferencesKey("notifications_enabled")
        val LANGUAGE = stringPreferencesKey("language")
        val FONT_SIZE = stringPreferencesKey("font_size")
        val AUTO_PLAY_VIDEOS = booleanPreferencesKey("auto_play_videos")
    }

    override fun getSettings(): Flow<Settings> {
        return dataStore.data.map { preferences ->
            Settings(
                darkMode = preferences[PreferenceKeys.DARK_MODE] ?: false,
                notificationsEnabled = preferences[PreferenceKeys.NOTIFICATIONS] ?: true,
                language = preferences[PreferenceKeys.LANGUAGE] ?: "ko",
                fontSize = preferences[PreferenceKeys.FONT_SIZE]?.let {
                    FontSize.valueOf(it)
                } ?: FontSize.MEDIUM,
                autoPlayVideos = preferences[PreferenceKeys.AUTO_PLAY_VIDEOS] ?: true
            )
        }
    }

    override suspend fun updateSettings(settings: Settings) {
        dataStore.edit { preferences ->
            preferences[PreferenceKeys.DARK_MODE] = settings.darkMode
            preferences[PreferenceKeys.NOTIFICATIONS] = settings.notificationsEnabled
            preferences[PreferenceKeys.LANGUAGE] = settings.language
            preferences[PreferenceKeys.FONT_SIZE] = settings.fontSize.name
            preferences[PreferenceKeys.AUTO_PLAY_VIDEOS] = settings.autoPlayVideos
        }
    }

    override suspend fun updateDarkMode(enabled: Boolean) {
        dataStore.edit { preferences ->
            preferences[PreferenceKeys.DARK_MODE] = enabled
        }
    }

    override suspend fun updateNotifications(enabled: Boolean) {
        dataStore.edit { preferences ->
            preferences[PreferenceKeys.NOTIFICATIONS] = enabled
        }
    }

    override suspend fun updateLanguage(language: String) {
        dataStore.edit { preferences ->
            preferences[PreferenceKeys.LANGUAGE] = language
        }
    }
}

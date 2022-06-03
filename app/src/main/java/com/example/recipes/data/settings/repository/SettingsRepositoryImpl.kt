package com.example.recipes.data.settings.repository

import com.example.recipes.data.settings.datasources.local.SettingsLocalDataSource
import com.example.recipes.domain.settings.SettingsRepository

class SettingsRepositoryImpl(
    private val settingsLocalDataSource: SettingsLocalDataSource
) : SettingsRepository {

    override fun clearCache() = settingsLocalDataSource.clearCache()
}
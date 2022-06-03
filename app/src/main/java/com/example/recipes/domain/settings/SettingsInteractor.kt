package com.example.recipes.domain.settings


class SettingsInteractor(private val settingsRepository: SettingsRepository) {

    fun clearCache() = settingsRepository.clearCache()
}
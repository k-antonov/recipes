package com.example.recipes.domain.interactors

import com.example.recipes.domain.repositories.CommonRepository


class SettingsInteractor(private val commonRepository: CommonRepository) {

    fun clearCache() = commonRepository.clearCache()
}
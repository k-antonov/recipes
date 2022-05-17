package com.example.recipes.domain.interactors

import com.example.recipes.domain.repositories.RecipeRepository

class SettingsInteractor(private val recipeRepository: RecipeRepository) {

    fun execute() = recipeRepository.clearCache()
}
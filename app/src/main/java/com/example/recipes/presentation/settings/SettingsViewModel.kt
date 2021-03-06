package com.example.recipes.presentation.settings

import androidx.lifecycle.ViewModel
import com.example.recipes.domain.settings.SettingsInteractor
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val settingsInteractor: SettingsInteractor
) : ViewModel() {

    fun clearCache() = settingsInteractor.clearCache()
}
package com.example.recipes.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
//import com.example.recipes.domain.interactors.SettingsInteractor

//class SettingsViewModel(
//    private val settingsInteractor: SettingsInteractor
//) {
//
//    fun clearCache() = settingsInteractor.execute()
//}
//
//class SettingsViewModelFactory (
//    private val settingsInteractor: SettingsInteractor
//) : ViewModelProvider.Factory{
//    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
//        if (modelClass.isAssignableFrom(SettingsViewModel::class.java)) {
//            return SettingsViewModel(settingsInteractor)  as T
//        }
//        throw IllegalArgumentException("ViewModel Not Found")
//    }
//}
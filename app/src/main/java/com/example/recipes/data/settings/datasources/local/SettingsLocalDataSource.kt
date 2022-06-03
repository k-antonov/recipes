package com.example.recipes.data.settings.datasources.local

import android.content.Context
import com.example.recipes.data.core.datasources.local.LocalDataSource

class SettingsLocalDataSource(context: Context) : LocalDataSource(context) {

    fun clearCache() = database.clearAllTables()
}
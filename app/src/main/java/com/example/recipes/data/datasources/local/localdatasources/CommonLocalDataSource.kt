package com.example.recipes.data.datasources.local.localdatasources

import android.content.Context

class CommonLocalDataSource(context: Context) : LocalDataSource(context) {

    fun clearCache() = database.clearAllTables()
}
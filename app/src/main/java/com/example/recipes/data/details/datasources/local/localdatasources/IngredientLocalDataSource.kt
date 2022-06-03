package com.example.recipes.data.details.datasources.local.localdatasources

import android.content.Context
import com.example.recipes.data.core.datasources.local.LocalDataSource
import com.example.recipes.data.details.datasources.local.entities.IngredientDb

class IngredientLocalDataSource(context: Context) : LocalDataSource(context) {

    private val dao = database.getIngredientDao()

    fun insert(ingredientName: String): Long {
        var ingredientId = dao.getIdByName(ingredientName)
        if (ingredientId == 0L) {
            ingredientId = dao.insert(
                IngredientDb(
                    name = ingredientName
                )
            )
        }
        return ingredientId
    }
}
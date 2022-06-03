package com.example.recipes.data.details.datasources.local.localdatasources

import android.content.Context
import com.example.recipes.data.core.datasources.local.LocalDataSource
import com.example.recipes.data.details.datasources.local.entities.RecipesToIngredientsAndMeasures
import com.example.recipes.data.details.datasources.local.relations.RecipeWithIngredientAndMeasureRelation

class RecipesToIngredientsAndMeasuresLocalDataSource(context: Context) : LocalDataSource(context) {

    private val dao = database.getRecipesToIngredientsAndMeasuresDao()

    fun insert(recipeId: Long, ingredientId: Long, measureId: Long) {
        dao.insert(
            RecipesToIngredientsAndMeasures(
                recipeId = recipeId,
                ingredientId = ingredientId,
                measureId = measureId
            )
        )
    }

    fun loadById(id: Long): List<RecipeWithIngredientAndMeasureRelation> {
        return dao.getIngredientsAndMeasuresById(id)
    }

}
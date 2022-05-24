package com.example.recipes.data.datasources.local.localdatasources

import android.content.Context
import com.example.recipes.data.datasources.local.RecipeWithIngredientAndMeasureRelation
import com.example.recipes.data.datasources.local.RecipesToIngredientsAndMeasures

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
package com.example.recipes.data.details.datasources.local.localdatasources

import android.content.Context
import android.util.Log
import com.example.recipes.data.core.datasources.local.LocalDataSource
import com.example.recipes.data.details.datasources.local.entities.RecipeDb
import com.example.recipes.domain.details.DetailDomain
import com.example.recipes.domain.previews.PreviewDomain

class RecipeLocalDataSource(
    context: Context,
    private val recipesToIngredientsAndMeasuresLocalDataSource: RecipesToIngredientsAndMeasuresLocalDataSource
) : LocalDataSource(context) {

    private val dao = database.getRecipeDao()

    fun changeFavoriteStatus(recipeId: Long, isFavorite: Boolean) {
        return dao.updateFavoriteStatusById(recipeId, isFavorite)
    }

    fun insertDetail(entity: DetailDomain, categoryId: Long, cuisineId: Long): Long {
        return dao.insert(
            RecipeDb(
                id = entity.id,
                name = entity.name,
                categoryId = categoryId,
                cuisineId = cuisineId,
                instructions = entity.strInstructions,
                imageUrl = entity.imageUrl
            )
        )
    }

    fun loadPreviewsByCategoryOrCuisine(name: String): List<PreviewDomain> {
        Log.d("RecipeLocalDataSource", "name=$name")
        val list = dao.getPreviewsByCategoryOrCuisine(name)
        Log.d("RecipeLocalDataSource", "list=$list")
        return list
    }

    fun getPreviewsFavorite(): List<PreviewDomain> =
        dao.getPreviewsFavorite()

    fun loadDetail(recipeId: Long): DetailDomain? {

        val recipeWithCategoryAndCuisine = dao.getDetailsById(recipeId)
        Log.d("RecipeLocal", "recipeWithCategory=$recipeWithCategoryAndCuisine")
        if (recipeWithCategoryAndCuisine == null) return null

        val recipesToIngredientsAndMeasures =
            recipesToIngredientsAndMeasuresLocalDataSource.loadById(recipeId)

        val ingredients = mutableListOf<String>()
        val measures = mutableListOf<String>()
        for (relation in recipesToIngredientsAndMeasures) {
            ingredients.add(relation.ingredient.name)
            measures.add(relation.measure.name)
        }

        return DetailDomain(
            id = recipeId,
            name = recipeWithCategoryAndCuisine.recipe.name,
            nameCategory = recipeWithCategoryAndCuisine.category.name,
            nameCuisine = recipeWithCategoryAndCuisine.cuisine.name,
            strInstructions = recipeWithCategoryAndCuisine.recipe.instructions,
            imageUrl = recipeWithCategoryAndCuisine.recipe.imageUrl,
            ingredients = ingredients,
            measures = measures,
            isFavorite = recipeWithCategoryAndCuisine.recipe.isFavorite
        )
    }
}
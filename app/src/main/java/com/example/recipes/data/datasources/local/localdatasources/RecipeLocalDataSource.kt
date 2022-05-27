package com.example.recipes.data.datasources.local.localdatasources

import android.content.Context
import android.util.Log
import com.example.recipes.MyApplication.Companion.recipesToIngredientsAndMeasuresLocalDataSource
import com.example.recipes.data.datasources.local.RecipeDb
import com.example.recipes.data.datasources.local.RecipeWithCategoryAndCuisineRelation
import com.example.recipes.domain.entities.DetailDomain
import com.example.recipes.domain.entities.PreviewDomain

class RecipeLocalDataSource(
    context: Context
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

    // вроде бы проверка на существование такой записи не нужна, т. к.
    // в DAO указана OnConflictStrategy.REPLACE. Именно REPLACE, а не IGNORE,
    // т. к. открывая PreviewsFragment в БД вносится частичная информация о рецептах,
    // необходимая для отображения превью (id, name, imageUrl)
    private fun insertPreview(previewDomain: PreviewDomain): Long {
        return dao.insert(
            RecipeDb(
                id = previewDomain.id,
                name = previewDomain.name,
                imageUrl = previewDomain.imageUrl
            )
        )
    }

    fun insertPreviewList(list: List<PreviewDomain>) {
        for (item in list) {
            insertPreview(item)
        }
    }

    fun loadPreviewsByCategoryOrCuisine(name: String): List<PreviewDomain> {
        Log.d("RecipeLocalDataSource", "name=$name")
        val list = dao.getPreviewsByCategoryOrCuisine(name)
        Log.d("RecipeLocalDataSource", "list=$list")
        return list
    }

    fun loadDetail(recipeId: Long): DetailDomain? {

        val recipeWithCategoryAndCuisine = dao.getDetailsById(recipeId)
        if (!hasDetails(recipeWithCategoryAndCuisine)) return null

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
            nameCategory = recipeWithCategoryAndCuisine.category!!.name,
            nameCuisine = recipeWithCategoryAndCuisine.cuisine!!.name,
            strInstructions = recipeWithCategoryAndCuisine.recipe.instructions!!,
            imageUrl = recipeWithCategoryAndCuisine.recipe.imageUrl,
            ingredients = ingredients,
            measures = measures,
            isFavorite = recipeWithCategoryAndCuisine.recipe.isFavorite
        )
    }

    private fun hasDetails(relation: RecipeWithCategoryAndCuisineRelation): Boolean {
        return relation.category != null
    }
}
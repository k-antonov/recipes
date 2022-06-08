package com.example.recipes.data.details.datasources.local.daos

import androidx.room.*
import com.example.recipes.data.details.datasources.local.relations.RecipeWithCategoryAndCuisineRelation
import com.example.recipes.data.details.datasources.local.entities.RecipeDb
import com.example.recipes.domain.previews.PreviewDomain

@Dao
interface RecipeDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(recipeDb: RecipeDb): Long

    @Query("SELECT recipes.id, recipes.name, recipes.imageUrl " +
            "FROM recipes JOIN categories ON recipes.categoryId = categories.id " +
            "JOIN cuisines ON recipes.cuisineId = cuisines.id " +
            "WHERE categories.name = :name OR cuisines.name = :name")
    fun getPreviewsByCategoryOrCuisine(name: String): List<PreviewDomain>

    @Query("SELECT id, name, imageUrl FROM recipes WHERE isFavorite = 1 " +
            "ORDER BY addedToFavoritesAt")
    fun getPreviewsFavorite(): List<PreviewDomain>

    @Transaction
    @Query("SELECT * FROM recipes WHERE id = :id")
    fun getDetailsById(id: Long): RecipeWithCategoryAndCuisineRelation?

    @Query("UPDATE recipes SET isFavorite = :isFavorite, " +
            "addedToFavoritesAt = :timestamp WHERE id = :id")
    fun updateFavoriteStatusById(id: Long, isFavorite: Boolean, timestamp: Long = System.currentTimeMillis())

}
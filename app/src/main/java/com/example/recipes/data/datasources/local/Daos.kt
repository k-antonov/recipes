package com.example.recipes.data.datasources.local

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.recipes.domain.entities.PreviewDomain

@Dao
interface RecipeDao {

    @Query("SELECT COUNT(1) FROM recipes WHERE id = :id")
    fun isRecordExist(id: Long): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE) // может ignore?
    fun insert(recipeDb: RecipeDb)

//    @Query("SELECT id, name, imageUrl FROM recipes")
//    fun getPreviews(): LiveData<List<PreviewDomain>>

    @Query("SELECT id, name, imageUrl FROM recipes")
    fun getPreviews(): List<PreviewDomain>

    @Transaction
    @Query("SELECT * FROM recipes WHERE id = :id")
    fun getDetailsById(id: Long): RecipeWithCategoryAndCuisineRelation

}

@Dao
interface RecipesToIngredientsAndMeasuresDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(recipesToIngredientsAndMeasures: RecipesToIngredientsAndMeasures)

    @Transaction
    @Query("SELECT * FROM recipes_to_ingredients_and_measures")
    fun getDetails(): LiveData<List<AllRecipeInfoRelation>>

    @Transaction
    @Query("SELECT * FROM recipes_to_ingredients_and_measures WHERE recipeId = :id")
    fun getIngredientsAndMeasuresById(id: Long): List<RecipeWithIngredientAndMeasureRelation>
}


@Dao
interface CategoryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(categoryDb: CategoryDb): Long

    @Delete
    fun delete(categoryDb: CategoryDb)

    @Query("SELECT id FROM categories WHERE name = :name")
    fun getIdByName(name: String): Long
}

@Dao
interface CuisineDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(cuisineDb: CuisineDb): Long

    @Delete
    fun delete(cuisineDb: CuisineDb)

    @Query("SELECT id FROM cuisines WHERE name = :name")
    fun getIdByName(name: String): Long
}

@Dao
interface IngredientDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(ingredientDb: IngredientDb): Long

    @Query("SELECT id FROM ingredients WHERE name = :name")
    fun getIdByName(name: String): Long
}

@Dao
interface MeasureDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(measureDb: MeasureDb): Long

    @Query("SELECT id FROM measures WHERE name = :name")
    fun getIdByName(name: String): Long
}




package com.example.recipes.data.datasources.local

import androidx.room.*
import com.example.recipes.domain.entities.CategoryDomain
import com.example.recipes.domain.entities.CuisineDomain
import com.example.recipes.domain.entities.PreviewDomain

@Dao
interface RecipeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(recipeDb: RecipeDb): Long

    @Query("SELECT recipes.id, recipes.name, recipes.imageUrl " +
            "FROM recipes JOIN categories ON recipes.categoryId = categories.id " +
            "JOIN cuisines ON recipes.cuisineId = cuisines.id " +
            "WHERE categories.name = :name OR cuisines.name = :name")
    fun getPreviewsByCategoryOrCuisine(name: String): List<PreviewDomain>

    @Transaction
    @Query("SELECT * FROM recipes WHERE id = :id")
    fun getDetailsById(id: Long): RecipeWithCategoryAndCuisineRelation
}

@Dao
interface RecipesToIngredientsAndMeasuresDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(recipesToIngredientsAndMeasures: RecipesToIngredientsAndMeasures)

    @Transaction
    @Query("SELECT * FROM recipes_to_ingredients_and_measures WHERE recipeId = :id")
    fun getIngredientsAndMeasuresById(id: Long): List<RecipeWithIngredientAndMeasureRelation>
}

@Dao
interface CategoryDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(categoryDb: CategoryDb): Long

    @Query("SELECT id FROM categories WHERE name = :name")
    fun getIdByName(name: String): Long

    @Query("SELECT * FROM categories")
    fun selectAll(): List<CategoryDomain>
}

@Dao
interface CuisineDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(cuisineDb: CuisineDb): Long

    @Query("SELECT id FROM cuisines WHERE name = :name")
    fun getIdByName(name: String): Long

    @Query("SELECT * FROM cuisines")
    fun selectAll(): List<CuisineDomain>
}

@Dao
interface IngredientDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(ingredientDb: IngredientDb): Long

    @Query("SELECT id FROM ingredients WHERE name = :name")
    fun getIdByName(name: String): Long
}

@Dao
interface MeasureDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(measureDb: MeasureDb): Long

    @Query("SELECT id FROM measures WHERE name = :name")
    fun getIdByName(name: String): Long
}




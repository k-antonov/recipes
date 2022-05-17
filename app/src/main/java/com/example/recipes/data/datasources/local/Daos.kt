package com.example.recipes.data.datasources.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.recipes.domain.entities.PreviewDomain

@Dao
interface RecipeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(recipeDb: RecipeDb)

    @Query("SELECT * FROM recipes")
    fun getAll(): List<RecipeDb>

    @Query("SELECT id, name, imageUrl FROM recipes")
    fun getPreviews(): LiveData<List<PreviewDomain>>
}

@Dao
interface RecipesToIngredientsAndMeasuresDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(recipesToIngredientsAndMeasures: RecipesToIngredientsAndMeasures)
}


@Dao
interface CategoryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(categoryDb: CategoryDb): Long

}

@Dao
interface CuisineDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(cuisineDb: CuisineDb): Long

}

@Dao
interface IngredientDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(ingredientDb: IngredientDb)
}

@Dao
interface MeasureDao{

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(measureDb: MeasureDb)
}




package com.example.recipes

import android.app.Application
import com.example.recipes.data.datasources.local.localdatasources.*
import okhttp3.OkHttpClient

class MyApplication : Application() {
    companion object {
        private lateinit var instance: MyApplication
        lateinit var categoryLocalDataSource: CategoryLocalDataSource
        lateinit var cuisineLocalDataSource: CuisineLocalDataSource
        lateinit var ingredientLocalDataSource: IngredientLocalDataSource
        lateinit var measureLocalDataSource: MeasureLocalDataSource
        lateinit var recipeLocalDataSource: RecipeLocalDataSource
        lateinit var recipesToIngredientsAndMeasuresLocalDataSource: RecipesToIngredientsAndMeasuresLocalDataSource

        val client = OkHttpClient()

        fun get() = instance
    }

    init {
        instance = this
    }

    override fun onCreate() {
        super.onCreate()
        categoryLocalDataSource = CategoryLocalDataSource(this)
        cuisineLocalDataSource = CuisineLocalDataSource(this)
        ingredientLocalDataSource = IngredientLocalDataSource(this)
        measureLocalDataSource = MeasureLocalDataSource(this)
        recipeLocalDataSource = RecipeLocalDataSource(this)
        recipesToIngredientsAndMeasuresLocalDataSource = RecipesToIngredientsAndMeasuresLocalDataSource(this)
    }
}
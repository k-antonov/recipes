package com.example.recipes.data.datasources.cloud

import androidx.lifecycle.LiveData
import com.example.recipes.data.datasources.cloud.entities.CategoriesCloud
import com.example.recipes.data.datasources.cloud.entities.CategoryCloud
import com.example.recipes.data.datasources.cloud.entities.CuisinesCloud
import com.example.recipes.data.datasources.cloud.entities.RecipeCloud

interface RecipeApiService {
    fun getRecipeCloudList(): LiveData<Result<List<RecipeCloud>>>

    fun getCategoriesCloud(): LiveData<Result<CategoriesCloud>>
    fun getCuisinesCloud(): LiveData<Result<CuisinesCloud>>
}
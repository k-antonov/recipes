package com.example.recipes.data.datasources.cloud

import androidx.lifecycle.LiveData
import com.example.recipes.data.datasources.cloud.entities.*

interface RecipeApiService {
    fun getRecipeCloudList(): LiveData<Result<List<RecipeCloud>>>

    fun getCategoriesCloud(): LiveData<Result<CategoriesCloud>>
    fun getCuisinesCloud(): LiveData<Result<CuisinesCloud>>
    fun getPreviewsCloud(endpoint: String): LiveData<Result<PreviewsCloud>>
    fun getDetailsCloud(endpoint: String): LiveData<Result<DetailsCloud>>
}
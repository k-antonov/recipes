package com.example.recipes.data.datasources.remote

import androidx.lifecycle.LiveData
import com.example.recipes.data.datasources.remote.entities.*

interface RecipeApiService {

    fun getCategoriesRemote(): LiveData<Result<CategoriesRemote>>

    fun getCuisinesRemote(): LiveData<Result<CuisinesRemote>>

    fun getPreviewsRemote(endpoint: String): LiveData<Result<PreviewsRemote>>

    fun getDetailsRemote(recipeId: Long): LiveData<Result<DetailsRemote>>

}
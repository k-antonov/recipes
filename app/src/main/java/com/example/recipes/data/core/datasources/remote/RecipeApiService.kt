package com.example.recipes.data.core.datasources.remote

import androidx.lifecycle.LiveData
import com.example.recipes.data.categories.datasources.remote.CategoriesRemote
import com.example.recipes.data.cuisines.datasources.remote.CuisinesRemote
import com.example.recipes.data.details.datasources.remote.DetailsRemote
import com.example.recipes.data.previews.datasources.remote.PreviewsRemote

interface RecipeApiService {

    fun getCategoriesRemote(): LiveData<Result<CategoriesRemote>>

    fun getCuisinesRemote(): LiveData<Result<CuisinesRemote>>

    fun getPreviewsRemote(endpoint: String): LiveData<Result<PreviewsRemote>>

    fun getDetailsRemote(recipeId: Long): LiveData<Result<DetailsRemote>>

}
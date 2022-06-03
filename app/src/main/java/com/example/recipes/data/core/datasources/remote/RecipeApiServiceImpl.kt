package com.example.recipes.data.core.datasources.remote

import androidx.lifecycle.LiveData
import com.example.recipes.data.categories.datasources.remote.CategoriesRemote
import com.example.recipes.data.categories.datasources.remote.CategoriesRemoteRequester
import com.example.recipes.data.cuisines.datasources.remote.CuisinesRemote
import com.example.recipes.data.cuisines.datasources.remote.CuisinesRemoteRequester
import com.example.recipes.data.details.datasources.remote.DetailsRemote
import com.example.recipes.data.details.datasources.remote.DetailsRemoteRequester
import com.example.recipes.data.previews.datasources.remote.PreviewsRemote
import com.example.recipes.data.previews.datasources.remote.PreviewsRemoteRequester
import okhttp3.OkHttpClient

class RecipeApiServiceImpl(private val okHttpClient: OkHttpClient) : RecipeApiService {

    override fun getCategoriesRemote(): LiveData<Result<CategoriesRemote>> {
        val requester = CategoriesRemoteRequester()
        return requester.execute(okHttpClient)
    }

    override fun getCuisinesRemote(): LiveData<Result<CuisinesRemote>> {
        val requester = CuisinesRemoteRequester()
        return requester.execute(okHttpClient)
    }

    override fun getPreviewsRemote(endpoint: String): LiveData<Result<PreviewsRemote>> {
        val requester = PreviewsRemoteRequester()
        return requester.execute(okHttpClient, endpoint)
    }

    override fun getDetailsRemote(recipeId: Long): LiveData<Result<DetailsRemote>> {
        val requester = DetailsRemoteRequester()
        return requester.execute(okHttpClient, recipeId.toString())
    }

}
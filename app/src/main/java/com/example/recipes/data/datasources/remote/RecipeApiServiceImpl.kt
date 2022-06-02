package com.example.recipes.data.datasources.remote

import androidx.lifecycle.LiveData
import com.example.recipes.data.datasources.remote.entities.*
import com.example.recipes.data.datasources.remote.requesters.*
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
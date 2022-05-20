package com.example.recipes.data.datasources.remote

import androidx.lifecycle.LiveData
import com.example.recipes.MyApplication.Companion.client
import com.example.recipes.data.datasources.remote.entities.*
import com.example.recipes.data.datasources.remote.requesters.*

class RecipeApiServiceImpl : RecipeApiService {

    // как тут избавиться от повторяющегося кода?
    override fun getCategoriesRemote(): LiveData<Result<CategoriesRemote>> {
        val requester = CategoriesRemoteRequester()
        return requester.execute(client)
    }

    override fun getCuisinesRemote(): LiveData<Result<CuisinesRemote>> {
        val requester = CuisinesRemoteRequester()
        return requester.execute(client)
    }

    override fun getPreviewsRemote(endpoint: String): LiveData<Result<PreviewsRemote>> {
        val requester = PreviewsRemoteRequester()
        return requester.execute(client, endpoint)
    }

    override fun getDetailsRemote(endpoint: String): LiveData<Result<DetailsRemote>> {
        val requester = DetailsRemoteRequester()
        return requester.execute(client, endpoint)
    }

}
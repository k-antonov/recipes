package com.example.recipes.data.api

import androidx.lifecycle.LiveData
import com.example.recipes.data.model.Recipe
import okhttp3.OkHttpClient

class RecipeApiService(private val client: OkHttpClient) : ApiService<Recipe> {
    private val getRequestCommand = GetRequest()

    override fun fetch(): LiveData<Result<List<Recipe>>> {
        return getRequestCommand.execute(client)
    }
}
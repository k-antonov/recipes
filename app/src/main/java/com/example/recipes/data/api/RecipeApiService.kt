package com.example.recipes.data.api

import android.content.pm.PackageManager
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.recipes.MyApplication
import com.example.recipes.data.model.Recipe
import com.example.recipes.utils.Resource
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.*
import java.io.IOException
import java.net.URL

// мб сделать синглтоном?
class RecipeApiService : ApiService<Recipe> {
    private val okHttpClient = OkHttpClient()

    private var mutableRecipes = MutableLiveData<Resource<List<Recipe>>>()

    val recipes: LiveData<Resource<List<Recipe>>>
        get() = mutableRecipes

    private companion object {
        val TAG: String = RecipeApiService::class.java.simpleName

        // todo переписать endpoint для гибкости
        const val stringUrl = "https://random-recipes.p.rapidapi.com/ai-quotes/2"
        val apiHostPair = "X-RapidAPI-Host" to "random-recipes.p.rapidapi.com"
        val apiKeyPair = "X-RapidAPI-Key" to apiKey

        val apiKey: String
            get() {
                val appInfo = MyApplication.get().packageManager
                    .getApplicationInfo(
                        MyApplication.get().packageName,
                        PackageManager.GET_META_DATA
                    )
                return appInfo.metaData["keyValue"].toString()
            }
    }

    override fun fetch(): Resource<List<Recipe>> {
        val recipeList = getRequest(stringUrl, apiHostPair, apiKeyPair)
        Log.d(TAG, "recipeList=$recipeList")
        recipeList?.let {
            Log.d(TAG, "Success")
            return Resource.success(it)
        }
        Log.d(TAG, "Error")
        return Resource.error(recipeList, null)
    }

    private fun getRequest(
        stringUrl: String,
        apiHost: Pair<String, String>,
        apiKey: Pair<String, String>
    ): List<Recipe>? {
        var result: List<Recipe>? = null
        try {
            val request = Request.Builder().url(URL(stringUrl)).get()
                .addHeader(apiHost.first, apiHost.second)
                .addHeader(apiKey.first, apiKey.second)
                .build()

            okHttpClient.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    Log.d(TAG, "onFailure: ${e.stackTraceToString()}")
                }

                override fun onResponse(call: Call, response: Response) {
                    result = parseResponse(response)
                    // как оповещать RecipeRepository?
                    mutableRecipes.postValue(Resource.success(result))
                }
            })
        } catch (e: Error) {
            Log.d(TAG, "Error when executing get request: ${e.localizedMessage}")
        }
        return result
    }

    private fun parseResponse(response: Response): List<Recipe>? {
        var recipes: List<Recipe>? = null

        try {
            val moshi: Moshi = Moshi.Builder()
                .addLast(KotlinJsonAdapterFactory())
                .build()
            val listType = Types.newParameterizedType(List::class.java, Recipe::class.java)
            val jsonAdapter: JsonAdapter<List<Recipe>> = moshi.adapter(listType)

            recipes = jsonAdapter.fromJson(response.body?.string() ?: "Empty response body")
            Log.d(TAG, recipes.toString())

        } catch (error: Error) {
            Log.d(TAG, "Error when parsing JSON: $error")
        }

        return recipes
    }
}
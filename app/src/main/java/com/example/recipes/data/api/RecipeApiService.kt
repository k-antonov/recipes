package com.example.recipes.data.api

import android.content.pm.PackageManager
import android.util.Log
import com.example.recipes.MyApplication
import com.example.recipes.data.model.Recipe
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.Request
import java.net.URL

class RecipeApiService : ApiService<Recipe> {
    private val okHttpClient = OkHttpClient()

    private companion object {
        val TAG: String = RecipeApiService::class.java.simpleName
    }

    override fun fetch(): Recipe? {
        return getRecipe(
            "https://random-recipes.p.rapidapi.com/ai-quotes/1",
            "X-RapidAPI-Host" to "random-recipes.p.rapidapi.com",
            "X-RapidAPI-Key" to getApiKey()
        )
    }

    private fun getRequest(
        stringUrl: String,
        apiHost: Pair<String, String>,
        apiKey: Pair<String, String>
    ): String? {
        var result: String? = null

        try {
            val url = URL(stringUrl)
            val request = Request.Builder()
                .url(url)
                .get()
                .addHeader(apiHost.first, apiHost.second)
                .addHeader(apiKey.first, apiKey.second)
                .build()

            val response = okHttpClient.newCall(request).execute()
            result = response.body?.string()
        } catch (e: Error) {
            println("Error when executing get request: ${e.localizedMessage}")
        }
        return result
    }

    private fun getRecipe(
        stringUrl: String,
        apiHost: Pair<String, String>,
        apiKey: Pair<String, String>
    ): Recipe? {
        var recipe: Recipe? = null
        // todo подумать как заменить GlobalScope
        GlobalScope.launch(Dispatchers.IO) {
            val result = getRequest(stringUrl, apiHost, apiKey)
            Log.d(TAG, result.toString())

            val moshi: Moshi = Moshi.Builder()
                .addLast(KotlinJsonAdapterFactory())
                .build()
            val listType = Types.newParameterizedType(List::class.java, Recipe::class.java)
            val jsonAdapter: JsonAdapter<List<Recipe>> = moshi.adapter(listType)

            val resultObject = jsonAdapter.fromJson(result)
            Log.d(TAG, resultObject.toString())
            // todo update recipe
        }
        return recipe
    }

    private fun getApiKey(): String {
        val appInfo = MyApplication.get().packageManager
            .getApplicationInfo(MyApplication.get().packageName, PackageManager.GET_META_DATA)
        return appInfo.metaData["keyValue"].toString()
    }
}
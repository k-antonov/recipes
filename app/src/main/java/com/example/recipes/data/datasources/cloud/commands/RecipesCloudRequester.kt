package com.example.recipes.data.datasources.cloud.commands

import android.content.pm.PackageManager
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.recipes.MyApplication
import com.example.recipes.data.datasources.cloud.entities.RecipeCloud
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.*
import java.io.IOException
import java.net.URL

class RecipesCloudRequester : Command<LiveData<Result<List<RecipeCloud>>>> {
    private val mutableRecipes = MutableLiveData<Result<List<RecipeCloud>>>()
    val recipes: LiveData<Result<List<RecipeCloud>>>
        get() = mutableRecipes

    private companion object {
        val TAG: String = RecipesCloudRequester::class.java.simpleName
        // todo переписать endpoint для гибкости
        const val stringUrl = "https://random-recipes.p.rapidapi.com/ai-quotes/10"
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

    override fun execute(client: OkHttpClient): LiveData<Result<List<RecipeCloud>>> {

        try {
            val request = Request.Builder().url(URL(stringUrl)).get()
                .addHeader(apiHostPair.first, apiHostPair.second)
                .addHeader(apiKeyPair.first, apiKeyPair.second)
                .build()

            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    Log.d(TAG, "onFailure: ${e.stackTraceToString()}")
                }

                override fun onResponse(call: Call, response: Response) {
                    val result = parseResponse(response)
                    Log.d(TAG, "onResponse: $result")
                    mutableRecipes.postValue(result)
                }
            })
        } catch (e: Error) {
            Log.d(TAG, "Error when executing get request: ${e.localizedMessage}")
        }
        Log.d(TAG, "returning null")
        return recipes
    }

    private fun parseResponse(response: Response): Result<List<RecipeCloud>> {
        val moshi: Moshi = Moshi.Builder()
            .addLast(KotlinJsonAdapterFactory())
            .build()
        val listType = Types.newParameterizedType(List::class.java, RecipeCloud::class.java)
        val jsonAdapter: JsonAdapter<List<RecipeCloud>> = moshi.adapter(listType)

        return runCatching {
            val res = jsonAdapter.fromJson(response.body!!.string())
            res!! // уместно ли тут "!!"?
        }
    }
}
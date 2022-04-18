package com.example.recipes.data.api

import android.content.pm.PackageManager
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.recipes.MyApplication
import com.example.recipes.data.model.Recipe
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.*
import java.io.IOException
import java.net.URL

class GetRequest : Command<LiveData<Result<List<Recipe>>>> {
    private val mutableRecipes = MutableLiveData<Result<List<Recipe>>>()
    val recipes: LiveData<Result<List<Recipe>>>
        get() = mutableRecipes

    private companion object {
        val TAG: String = GetRequest::class.java.simpleName
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

    override fun execute(client: OkHttpClient): LiveData<Result<List<Recipe>>> {

        // можно ли тут try/catch заменить на Result?
        // Тогда придётся изменить возвращаемый тип на Result<LiveData<Result<List<Recipe>>>>
        // или есть красивый способ?

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
                    mutableRecipes.postValue(result)
                }
            })
        } catch (e: Error) {
            Log.d(TAG, "Error when executing get request: ${e.localizedMessage}")
        }
        return recipes
    }

    private fun parseResponse(response: Response): Result<List<Recipe>> {
        val moshi: Moshi = Moshi.Builder()
            .addLast(KotlinJsonAdapterFactory())
            .build()
        val listType = Types.newParameterizedType(List::class.java, Recipe::class.java)
        val jsonAdapter: JsonAdapter<List<Recipe>> = moshi.adapter(listType)

        return runCatching {
            val res = jsonAdapter.fromJson(response.body!!.string())
            res!! // уместно ли тут "!!"?
        }
    }
}
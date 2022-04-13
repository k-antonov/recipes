package com.example.recipes.data.api

import android.content.pm.PackageManager
import android.util.Log
import com.example.recipes.MyApplication
import com.example.recipes.data.model.Recipe
import com.example.recipes.utils.Resource
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.*
import java.net.URL

// мб сделать синглтоном?
class RecipeApiService : ApiService<Recipe> {
    private val okHttpClient = OkHttpClient()

    private companion object {
        val TAG: String = RecipeApiService::class.java.simpleName

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
        val recipeList = parseResponse(stringUrl, apiHostPair, apiKeyPair)
        Log.d(
            TAG,
            "recipeList=$recipeList"
        )
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


//            okHttpClient.newCall(request).enqueue(object : Callback {
//                override fun onFailure(call: Call, e: IOException) {
//                    Log.d(TAG, "onFailure: ${e.stackTraceToString()}")
//                }
//
//                override fun onResponse(call: Call, response: Response) {
//                    result = response.body?.string()
//                    Log.d(TAG, "result=$result")
//                }
//            })


            val response = okHttpClient.newCall(request).execute()
            result = response.body?.string()
        } catch (e: Error) {
            Log.d(TAG, "Error when executing get request: ${e.localizedMessage}")
        }
        return result
    }

    private fun parseResponse(
        stringUrl: String,
        apiHost: Pair<String, String>,
        apiKey: Pair<String, String>
    ): List<Recipe>? {
        var recipes: List<Recipe>? = null
        // todo подумать как заменить GlobalScope и диспетчер
        // Проблема, видимо, в корутине. Код выполняется в отдельном диспетчере, а в текущий поток
        // передаётся null, из-за чего recipeList=null
        GlobalScope.launch(Dispatchers.IO) {
            val result = getRequest(stringUrl, apiHost, apiKey)

            if (result != null) {
                try {
                    val moshi: Moshi = Moshi.Builder()
                        .addLast(KotlinJsonAdapterFactory())
                        .build()
                    val listType = Types.newParameterizedType(List::class.java, Recipe::class.java)
                    val jsonAdapter: JsonAdapter<List<Recipe>> = moshi.adapter(listType)

                    recipes = jsonAdapter.fromJson(result)
                    Log.d(TAG, recipes.toString())

                } catch (error: Error) {
                    Log.d(TAG, "Error when parsing JSON: $error")
                }
            } else {
                Log.d(TAG, "GET request returned no response")
            }
        }
        return recipes
    }
}
package com.example.recipes.data.datasources.cloud.commands

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.recipes.data.datasources.cloud.entities.CategoriesCloud
import com.example.recipes.data.datasources.cloud.entities.CategoryCloud
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.*
import java.io.IOException
import java.net.URL

private const val ENDPOINT = "https://www.themealdb.com/api/json/v1/1/categories.php"

class CategoriesCloudRequester : Requester<LiveData<Result<CategoriesCloud>>> {

    private val mutableCategoriesCloud = MutableLiveData<Result<CategoriesCloud>>()
    val categoriesCloud: LiveData<Result<CategoriesCloud>>
        get() = mutableCategoriesCloud

    companion object {
        val TAG: String = CategoriesCloudRequester::class.java.simpleName
    }

    override fun execute(client: OkHttpClient): LiveData<Result<CategoriesCloud>> {
        try {
            val request = Request.Builder().url(URL(ENDPOINT)).get()
                .build()

            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    Log.d(TAG, "onFailure: ${e.stackTraceToString()}")
                }

                override fun onResponse(call: Call, response: Response) {
                    val result = parseResponse(response)
                    Log.d(TAG, "onResponse: $result")
                    mutableCategoriesCloud.postValue(result)
                }
            })
        } catch (e: Error) {
            Log.d(TAG, "Error when executing get request: ${e.localizedMessage}")
        }
        Log.d(TAG, "returning null")
        return categoriesCloud
    }

    private fun parseResponse(response: Response): Result<CategoriesCloud> {
        val moshi: Moshi = Moshi.Builder()
            .addLast(KotlinJsonAdapterFactory())
            .build()
        val jsonAdapter: JsonAdapter<CategoriesCloud> = moshi.adapter(CategoriesCloud::class.java)

        return runCatching {
            val res = jsonAdapter.fromJson(response.body!!.string())
            res!! // уместно ли тут "!!"?
        }
    }

}
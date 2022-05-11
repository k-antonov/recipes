package com.example.recipes.data.datasources.cloud.requesters

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.*
import java.io.IOException

abstract class BaseCloudRequester<T> {

    companion object {
        val TAG: String = BaseCloudRequester::class.java.simpleName
    }

    protected abstract val BASE_URL: String

    private val mutableEntitiesCloud = MutableLiveData<Result<T>>()

    fun execute(client: OkHttpClient, endpoint: String = ""): LiveData<Result<T>> {
        val url = "$BASE_URL$endpoint"
        try {
            val request = Request.Builder().url(java.net.URL(url)).get()
                .build()

            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    Log.d(TAG, "onFailure: ${e.stackTraceToString()}")
                    mutableEntitiesCloud.postValue(Result.failure(Exception("Network Error")))
                }

                override fun onResponse(call: Call, response: Response) {
                    val result = parseResponse(response, jsonAdapter)
                    Log.d(TAG, "onResponse: $result")
                    mutableEntitiesCloud.postValue(result)
                }
            })
        } catch (e: Error) {
            Log.d(TAG, "Error when executing get request: ${e.localizedMessage}")
        }
        return mutableEntitiesCloud
    }

    protected val moshi: Moshi = Moshi.Builder()
        .addLast(KotlinJsonAdapterFactory())
        .build()

    protected abstract val jsonAdapter: JsonAdapter<T>

    private fun parseResponse(response: Response, jsonAdapter: JsonAdapter<T>): Result<T> {
        return kotlin.runCatching {
            jsonAdapter.fromJson(response.body!!.string())!!
        }
    }
}
package com.example.recipes.data.datasources.cloud.requesters

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.recipes.data.datasources.cloud.entities.CuisinesCloud
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.*
import java.io.IOException
import java.net.URL

private const val ENDPOINT = "https://www.themealdb.com/api/json/v1/1/list.php?a=list"

// todo fix DRY violation
class CuisinesCloudRequester: Requester<LiveData<Result<CuisinesCloud>>> {
    private val mutableCuisinesCloud = MutableLiveData<Result<CuisinesCloud>>()
    val cuisinesCloud: LiveData<Result<CuisinesCloud>>
        get() = mutableCuisinesCloud

    companion object {
        val TAG: String = CuisinesCloudRequester::class.java.simpleName
    }

    override fun execute(client: OkHttpClient): LiveData<Result<CuisinesCloud>> {
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
                    mutableCuisinesCloud.postValue(result)
                }
            })
        } catch (e: Error) {
            Log.d(TAG, "Error when executing get request: ${e.localizedMessage}")
        }
        Log.d(TAG, "returning null")
        return cuisinesCloud
    }

    private fun parseResponse(response: Response): Result<CuisinesCloud> {
        val moshi: Moshi = Moshi.Builder()
            .addLast(KotlinJsonAdapterFactory())
            .build()
        val jsonAdapter: JsonAdapter<CuisinesCloud> = moshi.adapter(CuisinesCloud::class.java)

        return runCatching {
            val res = jsonAdapter.fromJson(response.body!!.string())
            res!! // уместно ли тут "!!"?
        }
    }
}
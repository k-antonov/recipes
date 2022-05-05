package com.example.recipes.data.datasources.cloud.requesters

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.recipes.data.datasources.cloud.entities.PreviewsCloud
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.*
import java.io.IOException
import java.net.URL

private const val BASE_URL = "https://www.themealdb.com/api/json/v1/1/filter.php?"

// todo fix DRY violation
class PreviewsCloudRequester: Requester<LiveData<Result<PreviewsCloud>>> {
    private val mutablePreviewsCloud = MutableLiveData<Result<PreviewsCloud>>()
    val previewsCloud: LiveData<Result<PreviewsCloud>>
        get() = mutablePreviewsCloud

    companion object {
        val TAG: String = PreviewsCloudRequester::class.java.simpleName
    }

    override fun execute(client: OkHttpClient, endpoint: String?): LiveData<Result<PreviewsCloud>> {
        val url = "$BASE_URL$endpoint"
        Log.d(TAG, "url=$url")
        try {
            val request = Request.Builder().url(URL(url)).get()
                .build()

            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    Log.d(TAG, "onFailure: ${e.stackTraceToString()}")
                }

                override fun onResponse(call: Call, response: Response) {
                    val result = parseResponse(response)
                    Log.d(TAG, "onResponse: $result")
                    mutablePreviewsCloud.postValue(result)
                }
            })
        } catch (e: Error) {
            Log.d(TAG, "Error when executing get request: ${e.localizedMessage}")
        }
        Log.d(TAG, "returning null")
        return previewsCloud
    }

    private fun parseResponse(response: Response): Result<PreviewsCloud> {
        val moshi: Moshi = Moshi.Builder()
            .addLast(KotlinJsonAdapterFactory())
            .build()
        val jsonAdapter: JsonAdapter<PreviewsCloud> = moshi.adapter(PreviewsCloud::class.java)

        return runCatching {
            val res = jsonAdapter.fromJson(response.body!!.string())
            res!! // уместно ли тут "!!"?
        }
    }
}
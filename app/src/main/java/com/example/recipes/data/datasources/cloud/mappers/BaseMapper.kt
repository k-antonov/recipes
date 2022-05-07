package com.example.recipes.data.datasources.cloud.mappers

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations

abstract class BaseMapper<FromList, From, To> {
    protected abstract fun mapToList(from: FromList): List<From>

    protected abstract fun mapEntity(from: From): To

    private fun mapList(from: List<From>): List<To> {
        return from.map { mapEntity(it) }
    }

    private fun mapResult(result: Result<FromList>): Result<List<To>> {
        val toList = result.map { mapToList(it) }
        return toList.map { list -> mapList(list) }
    }

    fun mapLiveData(liveData: LiveData<Result<FromList>>): LiveData<Result<List<To>>> {
        return Transformations.map(liveData) { mapResult(it) }
    }
}
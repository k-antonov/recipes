package com.example.recipes.data.core.datasources.remote

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations

// todo переписать маппер
// На мой взгляд, класс выглядит плохо, он не может правильно маппить DetailsCloud,
// потому что в отличие от остальных сущностей он не должен возвращать список
// (см. DetailsFragment - it.items[0], а не it.items)
//
// Хотелось бы написать что-то вроде следующего, но пока не понимаю как это должно работать
interface Mapper<From, To> {

    fun map(data: From): To

    class Base<From, To> {
        class EntityMapper<From, To> : Mapper<From, To> {
            override fun map(data: From): To {
                TODO("Not yet implemented")
            }
        }

        class ResultMapper<From, To> : Mapper<Result<From>, Result<To>> {
            override fun map(data: Result<From>): Result<To> {
                TODO("Not yet implemented")
            }
        }

        interface LiveDataMapper<From, To> : Mapper<LiveData<From>, LiveData<To>> {
            override fun map(data: LiveData<From>): LiveData<To> {
                TODO("Not yet implemented")
            }
        }
    }
}



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
package com.example.recipes.data.datasources.cloud.mappers

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.example.recipes.data.Mapper
import com.example.recipes.data.datasources.cloud.entities.RecipeCloud
import com.example.recipes.domain.entities.RecipeDetailsDomain

class RecipeCloudMapperToDetails : Mapper<RecipeCloud, RecipeDetailsDomain> {
    override fun mapToDomain(cloudEntity: RecipeCloud): RecipeDetailsDomain {
        return RecipeDetailsDomain(
            id = cloudEntity.id,
            title = cloudEntity.title,
            ingredients = cloudEntity.ingredients,
            instructions = cloudEntity.instructionData.map { it.text },
            imageUrl = cloudEntity.imageUrl
        )
    }

    private fun getEntityById(cloudEntities: List<RecipeCloud>, id: Int): RecipeCloud {
        return cloudEntities.first { it.id == id }
    }

    private fun mapToResultDomain(
        result: Result<List<RecipeCloud>>,
        id: Int
    ): Result<RecipeDetailsDomain> {
        // todo обработать failure
        val cloudList = result.getOrDefault(emptyList())
        val mappedEntity = mapToDomain(getEntityById(cloudList, id))
        return Result.success(mappedEntity)
    }

    fun mapToLiveData(liveData: LiveData<Result<List<RecipeCloud>>>, id: Int): LiveData<Result<RecipeDetailsDomain>> {
        return Transformations.map(liveData) {
            mapToResultDomain(it, id)
        }
    }
}
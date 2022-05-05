package com.example.recipes.data.datasources.cloud.mappers

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.example.recipes.data.Mapper
import com.example.recipes.data.datasources.cloud.entities.RecipeCloud
import com.example.recipes.domain.entities.RecipeDetailsDomain

class RecipeCloudMapperToDetails : Mapper<RecipeCloud, RecipeDetailsDomain> {
    override fun mapEntity(fromEntity: RecipeCloud): RecipeDetailsDomain {
        return RecipeDetailsDomain(
            id = fromEntity.id,
            title = fromEntity.title,
            ingredients = fromEntity.ingredients,
            instructions = fromEntity.instructionData.map { it.text },
            imageUrl = fromEntity.imageUrl
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
        val mappedEntity = mapEntity(getEntityById(cloudList, id))
        return Result.success(mappedEntity)
    }

    fun mapToLiveData(liveData: LiveData<Result<List<RecipeCloud>>>, id: Int): LiveData<Result<RecipeDetailsDomain>> {
        return Transformations.map(liveData) {
            mapToResultDomain(it, id)
        }
    }
}
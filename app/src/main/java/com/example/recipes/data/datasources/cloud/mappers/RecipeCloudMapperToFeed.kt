package com.example.recipes.data.datasources.cloud.mappers

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.example.recipes.data.Mapper
import com.example.recipes.data.datasources.cloud.entities.RecipeCloud
import com.example.recipes.domain.entities.RecipeFeedDomain

class RecipeCloudMapperToFeed : Mapper<RecipeCloud, RecipeFeedDomain> {

    override fun mapEntity(fromEntity: RecipeCloud): RecipeFeedDomain {
        return RecipeFeedDomain(
            id = fromEntity.id,
            title = fromEntity.title,
            imageUrl = fromEntity.imageUrl
        )
    }

    private fun mapToDomainList(cloudEntities: List<RecipeCloud>): List<RecipeFeedDomain> {
        return cloudEntities.map { mapEntity(it) }
    }

    private fun mapToResultDomainList(result: Result<List<RecipeCloud>>): Result<List<RecipeFeedDomain>> {
        return result.map { mapToDomainList(it) }
    }

    fun mapToLiveData(liveData: LiveData<Result<List<RecipeCloud>>>): LiveData<Result<List<RecipeFeedDomain>>> {
        return Transformations.map(liveData) { mapToResultDomainList(it) }
    }

}
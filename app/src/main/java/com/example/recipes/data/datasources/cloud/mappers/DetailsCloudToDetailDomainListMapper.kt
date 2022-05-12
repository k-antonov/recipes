package com.example.recipes.data.datasources.cloud.mappers

import com.example.recipes.data.datasources.cloud.entities.DetailCloud
import com.example.recipes.data.datasources.cloud.entities.DetailsCloud
import com.example.recipes.domain.entities.DetailDomain

class DetailsCloudToDetailDomainListMapper : BaseMapper<DetailsCloud, DetailCloud, DetailDomain>() {
    override fun mapToList(from: DetailsCloud): List<DetailCloud> {
        return from.detailsClouds
    }

    override fun mapEntity(from: DetailCloud): DetailDomain {
        val ingredients = mutableListOf(
            from.strIngredient1, from.strIngredient2, from.strIngredient3, from.strIngredient4,
            from.strIngredient5, from.strIngredient6, from.strIngredient7, from.strIngredient8,
            from.strIngredient9, from.strIngredient10, from.strIngredient11, from.strIngredient12,
            from.strIngredient13, from.strIngredient14, from.strIngredient15, from.strIngredient16,
            from.strIngredient17, from.strIngredient18, from.strIngredient19, from.strIngredient20
        ).filterNotNull().filter { it.isNotBlank() }

        val measures = mutableListOf(
            from.strMeasure1, from.strMeasure2, from.strMeasure3, from.strMeasure4,
            from.strMeasure5, from.strMeasure6, from.strMeasure7, from.strMeasure8,
            from.strMeasure9, from.strMeasure10, from.strMeasure11, from.strMeasure12,
            from.strMeasure13, from.strMeasure14, from.strMeasure15, from.strMeasure16,
            from.strMeasure17, from.strMeasure18, from.strMeasure19, from.strMeasure20,
        ).filterNotNull().filter { it.isNotBlank() }

        return DetailDomain(
            id = from.id,
            name = from.name,
            nameCategory = from.nameCategory,
            nameCuisine = from.nameCuisine,
            strInstructions = from.strInstructions,
            imageUrl = from.imageUrl,
            ingredients = ingredients,
            measures = measures
        )
    }
}
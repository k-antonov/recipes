package com.example.recipes.data.details.datasources.remote

import com.example.recipes.data.core.datasources.remote.BaseMapper
import com.example.recipes.domain.details.DetailDomain

class DetailsRemoteToDetailDomainListMapper :
    BaseMapper<DetailsRemote, DetailRemote, DetailDomain>() {
    override fun mapToList(from: DetailsRemote): List<DetailRemote> {
        return from.detailsRemote
    }

    override fun mapEntity(from: DetailRemote): DetailDomain {
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

        for (i in measures.size..ingredients.lastIndex) {
            (measures as MutableList).add("to taste")
        }

        return DetailDomain(
            id = from.id.toLong(),
            name = from.name,
            nameCategory = from.nameCategory,
            nameCuisine = from.nameCuisine,
            strInstructions = from.strInstructions,
            imageUrl = from.imageUrl,
            ingredients = ingredients,
            measures = measures,
            isFavorite = false
        )
    }
}
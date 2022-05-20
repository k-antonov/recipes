package com.example.recipes.data.datasources.remote.mappers

import com.example.recipes.data.datasources.remote.entities.CategoriesRemote
import com.example.recipes.data.datasources.remote.entities.CategoryRemote
import com.example.recipes.domain.entities.CategoryDomain

class CategoriesRemoteToCategoryDomainListMapper :
    BaseMapper<CategoriesRemote, CategoryRemote, CategoryDomain>() {

    override fun mapToList(from: CategoriesRemote): List<CategoryRemote> {
        return from.categoriesRemote
    }

    override fun mapEntity(from: CategoryRemote): CategoryDomain {
        return CategoryDomain(
            id = from.id.toInt(),
            name = from.name,
            imageUrl = from.imageUrl
        )
    }
}
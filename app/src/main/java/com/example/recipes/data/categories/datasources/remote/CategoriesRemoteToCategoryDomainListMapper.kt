package com.example.recipes.data.categories.datasources.remote

import com.example.recipes.data.core.datasources.remote.BaseMapper
import com.example.recipes.domain.categories.CategoryDomain

class CategoriesRemoteToCategoryDomainListMapper :
    BaseMapper<CategoriesRemote, CategoryRemote, CategoryDomain>() {

    override fun mapToList(from: CategoriesRemote): List<CategoryRemote> {
        return from.categoriesRemote
    }

    override fun mapEntity(from: CategoryRemote): CategoryDomain {
        return CategoryDomain(
            id = from.id.toLong(),
            name = from.name,
            imageUrl = from.imageUrl
        )
    }
}
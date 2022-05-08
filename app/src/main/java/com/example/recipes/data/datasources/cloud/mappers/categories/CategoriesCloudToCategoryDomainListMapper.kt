package com.example.recipes.data.datasources.cloud.mappers.categories

import com.example.recipes.data.datasources.cloud.entities.CategoriesCloud
import com.example.recipes.data.datasources.cloud.entities.CategoryCloud
import com.example.recipes.data.datasources.cloud.mappers.BaseMapper
import com.example.recipes.domain.entities.CategoryDomain

class CategoriesCloudToCategoryDomainListMapper :
    BaseMapper<CategoriesCloud, CategoryCloud, CategoryDomain>() {

    override fun mapToList(from: CategoriesCloud): List<CategoryCloud> {
        return from.categoriesCloud
    }

    override fun mapEntity(from: CategoryCloud): CategoryDomain {
        return CategoryDomain(
            id = from.id.toInt(),
            title = from.title,
            imageUrl = from.imageUrl
        )
    }
}
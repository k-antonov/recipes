package com.example.recipes.data.categories.datasources.local

import android.content.Context
import com.example.recipes.data.core.datasources.local.LocalDataSource
import com.example.recipes.domain.categories.CategoryDomain

class CategoryLocalDataSource(context: Context) : LocalDataSource(context) {

    private val dao = database.getCategoryDao()

    fun insert(categoryName: String, imageUrl: String): Long {
        var categoryId = dao.getIdByName(categoryName)
        if (categoryId == 0L) {
            categoryId = dao.insert(
                CategoryDb(
                    name = categoryName,
                    imageUrl = imageUrl
                )
            )
        }
        return categoryId
    }

    fun insertList(list: List<CategoryDomain>) {
        for (item in list) {
            insert(item.name, item.imageUrl)
        }
    }

    fun load(): List<CategoryDomain> {
        return dao.selectAll()
    }

}
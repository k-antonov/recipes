package com.example.recipes.data.datasources.local.localdatasources

import android.content.Context
import com.example.recipes.data.datasources.local.CategoryDb
import com.example.recipes.domain.entities.CategoryDomain

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
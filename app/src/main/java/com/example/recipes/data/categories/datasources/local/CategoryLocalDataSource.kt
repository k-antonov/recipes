package com.example.recipes.data.categories.datasources.local

import android.content.Context
import com.example.recipes.data.core.datasources.local.LocalDataSource
import com.example.recipes.domain.categories.CategoryDomain

class CategoryLocalDataSource(context: Context) : LocalDataSource(context) {

    private val dao = database.getCategoryDao()

    fun insert(categoryName: String, imageUrl: String) {
        dao.insert(CategoryDb(
            name = categoryName,
            imageUrl = imageUrl
        ))
    }

    fun insertList(list: List<CategoryDomain>) {
        for (item in list) {
            insert(item.name, item.imageUrl)
        }
    }

    fun getIdByName(name: String): Long = dao.getIdByName(name)

    fun load(): List<CategoryDomain> {
        return dao.selectAll()
    }

}
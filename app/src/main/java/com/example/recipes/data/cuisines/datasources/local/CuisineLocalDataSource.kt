package com.example.recipes.data.cuisines.datasources.local

import android.content.Context
import com.example.recipes.data.core.datasources.local.LocalDataSource
import com.example.recipes.domain.cuisines.CuisineDomain

class CuisineLocalDataSource(context: Context) : LocalDataSource(context) {

    private val dao = database.getCuisineDao()

    fun insert(cuisineName: String, imageUrl: String) {
        dao.insert(CuisineDb(
            name = cuisineName,
            imageUrl = imageUrl
        ))
    }

    fun insertList(list: List<CuisineDomain>) {
        for (item in list) {
            insert(item.name, item.imageUrl)
        }
    }

    fun getIdByName(name: String) = dao.getIdByName(name)

    fun load(): List<CuisineDomain> {
        return dao.selectAll()
    }
}
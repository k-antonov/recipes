package com.example.recipes.data.cuisines.datasources.local

import android.content.Context
import com.example.recipes.data.core.datasources.local.LocalDataSource
import com.example.recipes.domain.cuisines.CuisineDomain

class CuisineLocalDataSource(context: Context) : LocalDataSource(context) {

    private val dao = database.getCuisineDao()

    fun insert(cuisineName: String, imageUrl: String): Long {
        var cuisineId = dao.getIdByName(cuisineName)
        if (cuisineId == 0L) {
            cuisineId = dao.insert(
                CuisineDb(
                    name = cuisineName,
                    imageUrl = imageUrl
                )
            )
        }
        return cuisineId
    }

    fun insertList(list: List<CuisineDomain>) {
        for (item in list) {
            insert(item.name, item.imageUrl)
        }
    }

    fun load(): List<CuisineDomain> {
        return dao.selectAll()
    }
}
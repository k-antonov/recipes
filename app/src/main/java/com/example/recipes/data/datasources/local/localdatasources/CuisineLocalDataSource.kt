package com.example.recipes.data.datasources.local.localdatasources

import android.content.Context
import com.example.recipes.data.datasources.local.CuisineDb
import com.example.recipes.domain.entities.CuisineDomain

class CuisineLocalDataSource(context: Context) : LocalDataSource(context) {

    private val dao = database.getCuisineDao()

    fun insert(cuisineName: String, imageUrl: String): Long {
        // todo возможно стоит удалить проверку, т. к. onConflictStrategy.Ignore
        var cuisineId = dao.getIdByName(cuisineName)
        if (cuisineId == 0L) {
            cuisineId = dao.insert(
                CuisineDb(
                    // todo подумать над id
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
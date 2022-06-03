package com.example.recipes.data.details.datasources.local.localdatasources

import android.content.Context
import com.example.recipes.data.core.datasources.local.LocalDataSource
import com.example.recipes.data.details.datasources.local.entities.MeasureDb

class MeasureLocalDataSource(context: Context) : LocalDataSource(context) {

    private val dao = database.getMeasureDao()

    fun insert(measureName: String): Long {
        var measureId = dao.getIdByName(measureName)
        if (measureId == 0L) {
            measureId = dao.insert(
                MeasureDb(
                    name = measureName
                )
            )
        }
        return measureId
    }
}
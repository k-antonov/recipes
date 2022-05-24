package com.example.recipes.data.datasources.local.localdatasources

import android.content.Context
import com.example.recipes.data.datasources.local.MeasureDb

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
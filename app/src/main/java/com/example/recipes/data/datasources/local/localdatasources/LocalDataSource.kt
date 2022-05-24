package com.example.recipes.data.datasources.local.localdatasources

import android.content.Context
import com.example.recipes.data.datasources.local.AppDataBase

private const val DATABASE_NAME = "database.db"

abstract class LocalDataSource(context: Context) {

    protected val database = androidx.room.Room.databaseBuilder(
        context,
        AppDataBase::class.java,
        DATABASE_NAME
    ).allowMainThreadQueries().fallbackToDestructiveMigration().build()

}
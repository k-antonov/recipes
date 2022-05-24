package com.example.recipes.data.repositories

import com.example.recipes.MyApplication.Companion.commonLocalDataSource
import com.example.recipes.domain.repositories.CommonRepository

class CommonRepositoryImpl : CommonRepository {

    override fun clearCache() = commonLocalDataSource.clearCache()
}
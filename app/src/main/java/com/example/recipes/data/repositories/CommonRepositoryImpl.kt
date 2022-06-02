package com.example.recipes.data.repositories

import com.example.recipes.data.datasources.local.localdatasources.CommonLocalDataSource
import com.example.recipes.domain.repositories.CommonRepository

class CommonRepositoryImpl(
    private val commonLocalDataSource: CommonLocalDataSource
) : CommonRepository {

    override fun clearCache() = commonLocalDataSource.clearCache()
}
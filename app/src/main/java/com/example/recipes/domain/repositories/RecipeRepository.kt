package com.example.recipes.domain.repositories

import androidx.lifecycle.LiveData
import com.example.recipes.data.datasources.cloud.entities.CategoriesCloud
import com.example.recipes.data.datasources.cloud.entities.CategoryCloud
import com.example.recipes.domain.entities.*

interface RecipeRepository {

    fun getCategoryDomainList(): LiveData<Result<List<CategoryDomain>>>

    fun getCuisineDomainList(): LiveData<Result<List<CuisineDomain>>>

    fun getPreviewDomainList(endpoint: String): LiveData<Result<List<PreviewDomain>>>

    fun getDetailDomainList(endpoint: String): LiveData<Result<List<DetailDomain>>>
}
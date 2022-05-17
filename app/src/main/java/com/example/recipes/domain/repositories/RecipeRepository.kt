package com.example.recipes.domain.repositories

import androidx.lifecycle.LiveData
import com.example.recipes.domain.entities.CategoryDomain
import com.example.recipes.domain.entities.CuisineDomain
import com.example.recipes.domain.entities.DetailDomain
import com.example.recipes.domain.entities.PreviewDomain

interface RecipeRepository {

    fun getCategoryDomainList(): LiveData<Result<List<CategoryDomain>>>

    fun getCuisineDomainList(): LiveData<Result<List<CuisineDomain>>>

    fun getPreviewDomainList(endpoint: String): LiveData<Result<List<PreviewDomain>>>

    fun getDetailDomainList(endpoint: String): LiveData<Result<List<DetailDomain>>>

    fun getLocalPreviewDomainList(): LiveData<Result<List<PreviewDomain>>>
}
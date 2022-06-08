package com.example.recipes.presentation.categories

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.example.recipes.domain.categories.CategoryDomain
import com.example.recipes.domain.categories.CategoriesInteractor
import com.example.recipes.domain.cuisines.CuisinesInteractor
import com.example.recipes.presentation.core.viewmodel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CategoriesViewModel @Inject constructor(
    private val categoriesInteractor: CategoriesInteractor,
    cuisinesInteractor: CuisinesInteractor
) : BaseViewModel<CategoryDomain>(categoriesInteractor) {

    override val liveDataFromInteractor: LiveData<Result<List<CategoryDomain>>>
        get() = Transformations.switchMap(reloadTrigger) {
            categoriesInteractor.execute()
        }

    init {
        fetch()
        cuisinesInteractor.execute()
    }
}
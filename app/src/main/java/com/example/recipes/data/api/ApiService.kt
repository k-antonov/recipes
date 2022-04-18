package com.example.recipes.data.api

import androidx.lifecycle.LiveData
import com.example.recipes.data.model.Recipe

interface ApiService<T> {
    fun fetch() : LiveData<Result<List<Recipe>>>
}
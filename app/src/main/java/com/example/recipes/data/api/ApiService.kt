package com.example.recipes.data.api

import com.example.recipes.utils.Resource

interface ApiService<T> {
    fun fetch() : Resource<List<T>>
}
package com.example.recipes.data.api

interface ApiService<T> {
    fun fetch() : List<T>?
}
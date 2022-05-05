package com.example.recipes.presentation.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.recipes.MyApplication
import com.example.recipes.R
import com.example.recipes.data.datasources.cloud.RecipeApiService
import com.example.recipes.data.datasources.cloud.RecipeApiServiceImpl
import com.example.recipes.data.datasources.cloud.commands.CategoriesCloudRequester
import com.example.recipes.data.repositories.RecipeRepositoryImpl
import com.example.recipes.databinding.ActivityMainBinding
import com.example.recipes.domain.interactors.CategoriesInteractor
import com.example.recipes.domain.interactors.RecipeDetailsInteractor
import com.example.recipes.domain.interactors.RecipeFeedInteractor

// todo перенести (DI)
val recipeApiService: RecipeApiService = RecipeApiServiceImpl()
val recipeRepository = RecipeRepositoryImpl(recipeApiService)

val recipeFeedInteractor = RecipeFeedInteractor(recipeRepository)
val recipeDetailsInteractor = RecipeDetailsInteractor(recipeRepository)

val categoriesInteractor = CategoriesInteractor(recipeRepository)

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        CategoriesCloudRequester().execute(client = MyApplication.client)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.main_fragment_container, SearchFragment.newInstance())
                .commit()
        }
    }
}
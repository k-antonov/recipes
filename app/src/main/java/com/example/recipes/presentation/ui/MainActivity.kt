package com.example.recipes.presentation.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.recipes.R
import com.example.recipes.data.datasources.remote.RecipeApiService
import com.example.recipes.data.datasources.remote.RecipeApiServiceImpl
import com.example.recipes.data.repositories.*
import com.example.recipes.databinding.ActivityMainBinding
import com.example.recipes.domain.interactors.*
//import com.example.recipes.presentation.ui.favorites.LocalPreviewsFragment
import com.example.recipes.presentation.ui.search.SearchFragment
import com.example.recipes.presentation.ui.settings.SettingsFragment


// todo перенести (DI)
val recipeApiService: RecipeApiService = RecipeApiServiceImpl()
//val recipeRepository = RecipeRepositoryImpl(recipeApiService, localDataSource)

val categoriesRepository = CategoriesRepositoryImpl(recipeApiService)
val cuisinesRepository = CuisinesRepositoryImpl(recipeApiService)
val previewsRepository = PreviewsRepositoryImpl(recipeApiService)
val detailsRepository = DetailsRepositoryImpl(recipeApiService)
val commonRepository = CommonRepositoryImpl()

val categoriesInteractor = CategoriesInteractor(categoriesRepository)
val cuisinesInteractor = CuisinesInteractor(cuisinesRepository)
val previewsInteractor = PreviewsInteractor(previewsRepository)
val detailsInteractor = DetailsInteractor(detailsRepository)

//val localPreviewsInteractor = LocalPreviewsInteractor(recipeRepository)
//val localDetailsInteractor = LocalDetailsInteractor(recipeRepository)
val settingsInteractor = SettingsInteractor(commonRepository)

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.main_fragment_container, SearchFragment.newInstance())
                .commit()
        }

        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when(item.itemId) {
                R.id.search -> {
                    replaceFragmentWith(SearchFragment.newInstance())
                    true
                }
                R.id.favorites -> {
//                    replaceFragmentWith(LocalPreviewsFragment.newInstance())
                    true
                }
                R.id.settings -> {
                    replaceFragmentWith(SettingsFragment.newInstance())
                    true
                }
                else -> false
            }
        }
    }

    private fun replaceFragmentWith(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.main_fragment_container, fragment)
            .commit()
    }
}
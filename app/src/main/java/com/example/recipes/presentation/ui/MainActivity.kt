package com.example.recipes.presentation.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.recipes.R
import com.example.recipes.data.datasources.remote.RecipeApiService
import com.example.recipes.data.datasources.remote.RecipeApiServiceImpl
import com.example.recipes.data.repositories.*
import com.example.recipes.databinding.ActivityMainBinding
import com.example.recipes.domain.interactors.*
import com.example.recipes.presentation.ui.favorites.FavoritePreviewsFragment
//import com.example.recipes.presentation.ui.favorites.LocalPreviewsFragment
import com.example.recipes.presentation.ui.search.SearchFragment
import com.example.recipes.presentation.ui.settings.SettingsFragment


// todo перенести (DI)
val recipeApiService: RecipeApiService = RecipeApiServiceImpl()

val categoriesRepository = CategoriesRepositoryImpl(recipeApiService)
val cuisinesRepository = CuisinesRepositoryImpl(recipeApiService)
val previewsRepository = PreviewsRepositoryImpl(recipeApiService)
val detailsRepository = DetailsRepositoryImpl(recipeApiService)
val commonRepository = CommonRepositoryImpl()
val favoritePreviewsRepository = FavoritePreviewsRepositoryImpl()

val categoriesInteractor = CategoriesInteractor(categoriesRepository)
val cuisinesInteractor = CuisinesInteractor(cuisinesRepository)
val previewsInteractor = PreviewsInteractor(previewsRepository)
val detailsInteractor = DetailsInteractor(detailsRepository)
val favoritePreviewsInteractor = FavoritePreviewsInteractor(favoritePreviewsRepository)
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
                    clearBackStack()
                    replaceFragmentWith(SearchFragment.newInstance())
                    true
                }
                R.id.favorites -> {
                    clearBackStack() // позже вернуть
                    replaceFragmentWith(FavoritePreviewsFragment.newInstance())
                    true
                }
                R.id.settings -> {
                    clearBackStack()
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

    private fun clearBackStack() {
        if (supportFragmentManager.backStackEntryCount > 0) {
            val first = supportFragmentManager.getBackStackEntryAt(0)
            supportFragmentManager.popBackStack(first.id, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        }
    }
}
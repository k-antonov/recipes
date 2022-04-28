package com.example.recipes.ui.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.recipes.R
import com.example.recipes.data.api.RecipeApiService
import com.example.recipes.data.api.RecipeApiServiceImpl
import com.example.recipes.data.repository.RecipeRepository
import com.example.recipes.databinding.ActivityMainBinding

// todo перенести (DI)
val recipeApiService: RecipeApiService = RecipeApiServiceImpl()
val recipeRepository = RecipeRepository(recipeApiService)

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.fragment_container_view, FeedFragment())
                .commit()
        }
    }
}
package com.example.recipes.ui.view

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.recipes.data.api.RecipeApiService
import com.example.recipes.data.repository.RecipeRepository
import com.example.recipes.databinding.ActivityMainBinding
import com.example.recipes.ui.viewmodel.MainViewModel
import com.example.recipes.ui.viewmodel.ViewModelFactory

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private companion object {
        val TAG: String = MainActivity::class.java.simpleName
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // todo перенести
        val recipeApiService = RecipeApiService()
        val recipeRepository = RecipeRepository(recipeApiService)
        val mainViewModel = ViewModelProvider(
            this,
            ViewModelFactory(recipeRepository)
        ).get(MainViewModel::class.java)

        mainViewModel.recipes.observe(this) {
            // adapter.notifyDataSetChanged()
            val title = it.data?.get(0)?.title
            binding.recipeTitle.text = title
        }

    }
}
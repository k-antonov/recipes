package com.example.recipes.ui.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.recipes.data.api.RecipeApiService
import com.example.recipes.data.api.RecipeApiServiceImpl
import com.example.recipes.data.repository.RecipeRepository
import com.example.recipes.databinding.ActivityMainBinding
import com.example.recipes.ui.adapter.RecipeAdapter
import com.example.recipes.ui.viewmodel.MainViewModel
import com.example.recipes.ui.viewmodel.ViewModelFactory

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var recipeAdapter: RecipeAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // todo перенести (DI)
        val recipeApiService: RecipeApiService = RecipeApiServiceImpl()
        val recipeRepository = RecipeRepository(recipeApiService)
        val mainViewModel = ViewModelProvider(
            this,
            ViewModelFactory(recipeRepository)
        ).get(MainViewModel::class.java)

        binding.recyclerView.layoutManager = LinearLayoutManager(this)

        mainViewModel.recipes.observe(this) {
            recipeAdapter = RecipeAdapter(it)
            binding.recyclerView.adapter = recipeAdapter
        }

    }
}
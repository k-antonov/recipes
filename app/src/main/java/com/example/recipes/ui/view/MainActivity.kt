package com.example.recipes.ui.view

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.recipes.data.api.RecipeApiService
import com.example.recipes.data.api.RecipeApiServiceImpl
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

        // todo перенести (DI)
        val recipeApiService: RecipeApiService = RecipeApiServiceImpl()
        val recipeRepository = RecipeRepository(recipeApiService)
        val mainViewModel = ViewModelProvider(
            this,
            ViewModelFactory(recipeRepository)
        ).get(MainViewModel::class.java)

        mainViewModel.recipes.observe(this) { result ->
            // adapter.notifyDataSetChanged()

            result.onSuccess {
                // todo убрать хардкод; применить адаптер
                binding.recipeTitle.text = it[0].title
            }

            result.onFailure {
                Log.d(TAG, it.localizedMessage ?: "result.onFailure")
            }
        }

    }
}
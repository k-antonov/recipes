package com.example.recipes.ui.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.recipes.databinding.FragmentRecipeDetailsBinding
import com.example.recipes.ui.ImageDownloader
import com.example.recipes.ui.viewmodel.RecipeDetailsViewModel
import com.example.recipes.ui.viewmodel.RecipeDetailsViewModelFactory

class RecipeDetailsFragment : Fragment() {
    private lateinit var binding: FragmentRecipeDetailsBinding

    private val recipeId: Int
        get() = requireArguments().getInt(ARG_RECIPE_ID)

    // проблема в обращении к viewModel
    private val viewModel: RecipeDetailsViewModel by viewModels { RecipeDetailsViewModelFactory(recipeRepository, recipeId) }

    companion object {
        private val TAG = RecipeDetailsFragment::class.java.simpleName
        private const val ARG_RECIPE_ID = "ARG_RECIPE_ID"

        fun newInstance(recipeId: Int): RecipeDetailsFragment {
            val fragment = RecipeDetailsFragment()
            fragment.arguments = bundleOf(ARG_RECIPE_ID to recipeId)
            Log.d(TAG, "arguments=${fragment.arguments}")
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRecipeDetailsBinding.inflate(layoutInflater, container, false)

        Log.d(TAG, viewModel.recipe.title)

        val recipe = viewModel.recipe

        binding.recipeTitle.text = recipe.title
        ImageDownloader.load(binding.recipeImage, recipe.imageUrl)
        val ingredientsAdapter = ArrayAdapter(
            requireContext(), android.R.layout.simple_list_item_1,
            recipe.ingredients
        )
        binding.ingredientsList.adapter = ingredientsAdapter

        val instructionsAdapter = ArrayAdapter(
            requireContext(), android.R.layout.simple_list_item_1,
            // todo написать нормальный маппер
            recipe.instructions.map { it.text }
        )
        binding.instructionsList.adapter = instructionsAdapter

        return binding.root
    }
}
package com.example.recipes.presentation.ui

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
import com.example.recipes.presentation.ImageDownloader
import com.example.recipes.presentation.viewmodels.RecipeDetailsViewModel
import com.example.recipes.presentation.viewmodels.RecipeDetailsViewModelFactory

class RecipeDetailsFragment : Fragment() {
    private lateinit var binding: FragmentRecipeDetailsBinding

    private val recipeId: Int
        get() = requireArguments().getInt(ARG_RECIPE_ID)

    private val viewModel: RecipeDetailsViewModel by viewModels {
        RecipeDetailsViewModelFactory(recipeDetailsInteractor, recipeId)
    }

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

        viewModel.recipeDetails.observe(viewLifecycleOwner) {
            binding.recipeTitle.text = it.title
            ImageDownloader.load(binding.recipeImage, it.imageUrl)

            val ingredientsAdapter = ArrayAdapter(
                requireContext(), android.R.layout.simple_list_item_1, it.ingredients
            )
            binding.ingredientsList.adapter = ingredientsAdapter

            val instructionsAdapter = ArrayAdapter(
                requireContext(), android.R.layout.simple_list_item_1, it.instructions
            )
            binding.instructionsList.adapter = instructionsAdapter
        }

        return binding.root
    }
}
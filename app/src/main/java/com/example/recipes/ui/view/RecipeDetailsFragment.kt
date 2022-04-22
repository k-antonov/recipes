package com.example.recipes.ui.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.setFragmentResultListener
import com.example.recipes.databinding.FragmentRecipeDetailsBinding
import com.example.recipes.ui.ImageDownloader
import com.example.recipes.ui.viewmodel.FeedViewModel
import com.example.recipes.ui.viewmodel.ViewModelFactory

class RecipeDetailsFragment : Fragment() {

    private lateinit var binding: FragmentRecipeDetailsBinding

    private val viewModel: FeedViewModel by activityViewModels { ViewModelFactory(recipeRepository) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRecipeDetailsBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setFragmentResultListener(RECIPE_DETAILS_KEY) { _, bundle ->
            val position = bundle.getInt(RECIPE_POSITION_KEY)

            viewModel.recipes.value?.get(position)?.let { recipe ->
                binding.recipeTitle.text = recipe.title

                ImageDownloader.load(binding.recipeImage, recipe.imageUrl)
                val ingredientsAdapter = ArrayAdapter(
                    requireContext(), android.R.layout.simple_list_item_1,
                    recipe.ingredients
                )
                binding.ingredientsList.adapter = ingredientsAdapter

                val instructionsAdapter = ArrayAdapter(
                    requireContext(), android.R.layout.simple_list_item_1,
                    recipe.instructions.map { it.text }
                )
                binding.instructionsList.adapter = instructionsAdapter
            }
        }
    }

}
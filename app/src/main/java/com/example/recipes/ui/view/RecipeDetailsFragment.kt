package com.example.recipes.ui.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import com.example.recipes.databinding.FragmentRecipeDetailsBinding
import com.example.recipes.ui.viewmodel.FeedViewModel
import com.example.recipes.ui.viewmodel.ViewModelFactory
import kotlin.properties.Delegates

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
            binding.recipeTitle.text = viewModel.recipes.value?.get(position)?.title ?: ""
        }
    }
}
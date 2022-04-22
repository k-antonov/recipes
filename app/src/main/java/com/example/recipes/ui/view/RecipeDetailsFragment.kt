package com.example.recipes.ui.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
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

            viewModel.recipes.value?.get(position)?.let {
                binding.recipeTitle.text = it.title

                ImageDownloader.load(binding.recipeImage, it.imageUrl)
                val adapter = ArrayAdapter(
                    requireContext(), android.R.layout.simple_list_item_1,
                    it.ingredients
                )
                binding.ingredientsList.adapter = adapter
                binding.ingredientsList.setSize()
                binding.ingredientsList.setFooterDividersEnabled(false)
            }
        }
    }

    private fun ListView.setSize() {
        var totalHeight = 0
        for (position in 0 until adapter.count) {
            val listItem = adapter.getView(position, null, this)
            listItem.measure(0, 0)
            totalHeight += listItem.measuredHeight
        }
        layoutParams.height = totalHeight + dividerHeight + adapter.count
    }
}
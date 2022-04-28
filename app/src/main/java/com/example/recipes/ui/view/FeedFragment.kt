package com.example.recipes.ui.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.recipes.R
import com.example.recipes.databinding.FragmentFeedBinding
import com.example.recipes.ui.adapter.RecipeAdapter
import com.example.recipes.ui.viewmodel.FeedViewModel
import com.example.recipes.ui.viewmodel.ViewModelFactory

class FeedFragment : Fragment() {

    private lateinit var binding: FragmentFeedBinding
    private lateinit var recipeAdapter: RecipeAdapter

    private val viewModel: FeedViewModel by activityViewModels { ViewModelFactory(recipeRepository) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFeedBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())

        viewModel.recipes.observe(viewLifecycleOwner) {
            recipeAdapter = RecipeAdapter(it) { position ->
                val recipeId = it[position].id
                onListItemClick(recipeId)
            }
            binding.recyclerView.adapter = recipeAdapter
        }

    }

    private fun onListItemClick(recipeId: Int) {
        val fragment = RecipeDetailsFragment.newInstance(recipeId)
        replaceFragmentWith(fragment)
    }

    private fun replaceFragmentWith(fragment: Fragment) {
        requireActivity().supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container_view, fragment)
            .addToBackStack(null)
            .commit()
    }
}
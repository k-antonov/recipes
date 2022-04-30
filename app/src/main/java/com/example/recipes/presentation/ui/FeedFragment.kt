package com.example.recipes.presentation.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.recipes.R
import com.example.recipes.databinding.FragmentFeedBinding
import com.example.recipes.presentation.adapter.RecipeAdapter
import com.example.recipes.presentation.viewmodels.RecipeFeedViewModel
import com.example.recipes.presentation.viewmodels.RecipeFeedViewModelFactory

class FeedFragment : Fragment() {

    private lateinit var binding: FragmentFeedBinding
    private lateinit var recipeAdapter: RecipeAdapter

    companion object {
        private val TAG = FeedFragment::class.java.simpleName
    }

    private val viewModel: RecipeFeedViewModel by activityViewModels {
        RecipeFeedViewModelFactory(recipeFeedInteractor)
    }

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

        viewModel.recipeFeed.observe(viewLifecycleOwner) {
            Log.d(TAG, "$it")
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
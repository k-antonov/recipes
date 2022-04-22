package com.example.recipes.ui.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.recipes.databinding.FragmentFeedBinding
import com.example.recipes.ui.adapter.RecipeAdapter
import com.example.recipes.ui.viewmodel.FeedViewModel
import com.example.recipes.ui.viewmodel.ViewModelFactory

class FeedFragment : Fragment() {

    private lateinit var binding: FragmentFeedBinding
    private lateinit var recipeAdapter: RecipeAdapter

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

        val feedViewModel = ViewModelProvider(
            this,
            ViewModelFactory(recipeRepository)
        ).get(FeedViewModel::class.java)

        feedViewModel.recipes.observe(viewLifecycleOwner) {
            recipeAdapter = RecipeAdapter(it)
            binding.recyclerView.adapter = recipeAdapter
        }
    }

}
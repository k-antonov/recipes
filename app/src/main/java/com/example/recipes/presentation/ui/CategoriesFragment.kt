package com.example.recipes.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.example.recipes.databinding.FragmentCategoriesBinding
import com.example.recipes.presentation.adapters.CategoriesAdapter
import com.example.recipes.presentation.viewmodels.categories.CategoriesViewModel
import com.example.recipes.presentation.viewmodels.categories.CategoriesViewModelFactory

private const val COLUMNS = 2

class CategoriesFragment : Fragment() {

    private lateinit var binding: FragmentCategoriesBinding
    private lateinit var categoriesAdapter: CategoriesAdapter

    private val viewModel: CategoriesViewModel by viewModels {
        CategoriesViewModelFactory(categoriesInteractor)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCategoriesBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.categoriesRecyclerView.layoutManager = GridLayoutManager(requireContext(), COLUMNS)

        viewModel.categoryDomainList.observe(viewLifecycleOwner) {
            categoriesAdapter = CategoriesAdapter(it)
            binding.categoriesRecyclerView.adapter = categoriesAdapter
        }

    }

    companion object {
        @JvmStatic
        fun newInstance() = CategoriesFragment()
    }
}
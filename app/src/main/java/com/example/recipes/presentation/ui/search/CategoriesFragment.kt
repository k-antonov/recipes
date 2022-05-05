package com.example.recipes.presentation.ui.search

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.example.recipes.R
import com.example.recipes.databinding.FragmentCategoriesBinding
import com.example.recipes.domain.entities.CategoryDomain
import com.example.recipes.presentation.adapters.PreviewAdapter
import com.example.recipes.presentation.ui.categoriesInteractor
import com.example.recipes.presentation.viewmodels.categories.CategoriesViewModel
import com.example.recipes.presentation.viewmodels.categories.CategoriesViewModelFactory

private const val COLUMNS = 2

// todo fix DRY violation
class CategoriesFragment : Fragment() {

    private lateinit var binding: FragmentCategoriesBinding
    private lateinit var adapter: PreviewAdapter<CategoryDomain>

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
            adapter = PreviewAdapter(it) { position ->
                val endpoint = "c=${it[position].title}"
                Log.d("CategoriesFragment", endpoint)
                onListItemClick(endpoint)
            }
            binding.categoriesRecyclerView.adapter = adapter
        }

    }

    private fun onListItemClick(endpoint: String) {
        val fragment = PreviewFragment.newInstance(endpoint)
        replaceFragmentWith(fragment)
    }

    private fun replaceFragmentWith(fragment: Fragment) {
        requireActivity().supportFragmentManager
            .beginTransaction()
            .replace(R.id.main_fragment_container, fragment)
            .addToBackStack(null)
            .commit()
    }

    companion object {
        @JvmStatic
        fun newInstance() = CategoriesFragment()
    }
}
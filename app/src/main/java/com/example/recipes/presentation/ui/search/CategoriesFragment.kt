package com.example.recipes.presentation.ui.search

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.example.recipes.R
import com.example.recipes.domain.entities.CategoryDomain
import com.example.recipes.presentation.adapters.ClickableItemAdapter
import com.example.recipes.presentation.ui.GridListFragment
import com.example.recipes.presentation.ui.categoriesInteractor
import com.example.recipes.presentation.viewmodels.BaseViewModel
import com.example.recipes.presentation.viewmodels.categories.CategoriesViewModelFactory

class CategoriesFragment : GridListFragment<CategoryDomain>() {

    override val viewModel: BaseViewModel<CategoryDomain> by viewModels {
        CategoriesViewModelFactory(categoriesInteractor)
    }

    override val layoutResId = R.layout.fragment_categories

    // todo fix DRY violation
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = view.findViewById<RecyclerView>(R.id.categories_recycler_view)
        recyclerView.layoutManager = layoutManager

        val progressBar = view.findViewById<ProgressBar>(R.id.categories_progress_bar)

        viewModel.uiState.observe(viewLifecycleOwner) {
            when (it) {
                is BaseViewModel.UiState.Loading -> progressBar.visibility = View.VISIBLE
                is BaseViewModel.UiState.Success -> {
                    progressBar.visibility = View.GONE
                    adapter = ClickableItemAdapter(it.items) { position ->
                        val endpoint = "c=${it.items[position].name}"
                        onListItemClick(PreviewsFragment.newInstance(endpoint))
                    }
                    recyclerView.adapter = adapter
                }
                is BaseViewModel.UiState.Failure -> {
                    progressBar.visibility = View.INVISIBLE
                    showErrorDialog(it.throwable.message)
                }
            }
        }

    }

    companion object {
        @JvmStatic
        fun newInstance() = CategoriesFragment()
    }

}
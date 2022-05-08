package com.example.recipes.presentation.ui.search

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.example.recipes.R
import com.example.recipes.domain.entities.CategoryDomain
import com.example.recipes.presentation.adapters.PreviewAdapter
import com.example.recipes.presentation.ui.GridListFragment
import com.example.recipes.presentation.ui.categoriesInteractor
import com.example.recipes.presentation.viewmodels.BaseViewModel
import com.example.recipes.presentation.viewmodels.categories.CategoriesViewModelFactory

class CategoriesFragment : GridListFragment<CategoryDomain>() {

    override val viewModel: BaseViewModel<CategoryDomain> by viewModels {
        CategoriesViewModelFactory(categoriesInteractor)
    }

    override val layoutResId = R.layout.fragment_categories

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = view.findViewById<RecyclerView>(R.id.categories_recycler_view)
        recyclerView.layoutManager = layoutManager

        viewModel.itemDomainList.observe(viewLifecycleOwner) {
            adapter = PreviewAdapter(it) { position ->
                val endpoint = "c=${it[position].name}"
                onListItemClick(PreviewsFragment.newInstance(endpoint))
            }
            recyclerView.adapter = adapter
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = CategoriesFragment()
    }

}
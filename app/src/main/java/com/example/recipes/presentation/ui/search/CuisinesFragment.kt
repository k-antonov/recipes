package com.example.recipes.presentation.ui.search

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.example.recipes.R
import com.example.recipes.domain.entities.CuisineDomain
import com.example.recipes.presentation.adapters.PreviewAdapter
import com.example.recipes.presentation.ui.GridListFragment
import com.example.recipes.presentation.ui.cuisinesInteractor
import com.example.recipes.presentation.viewmodels.BaseViewModel
import com.example.recipes.presentation.viewmodels.cuisines.CuisinesViewModelFactory

class CuisinesFragment : GridListFragment<CuisineDomain>() {
    override val viewModel: BaseViewModel<CuisineDomain> by viewModels {
        CuisinesViewModelFactory(cuisinesInteractor)
    }

    override val layoutResId = R.layout.fragment_cuisines

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = view.findViewById<RecyclerView>(R.id.cuisines_recycler_view)
        recyclerView.layoutManager = layoutManager

        viewModel.itemDomainList.observe(viewLifecycleOwner) {
            adapter = PreviewAdapter(it) { position ->
                val endpoint = "a=${it[position].name}"
                onListItemClick(PreviewsFragment.newInstance(endpoint))
            }
            recyclerView.adapter = adapter
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = CuisinesFragment()
    }
}
package com.example.recipes.presentation.ui.search

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.example.recipes.R
import com.example.recipes.domain.entities.CuisineDomain
import com.example.recipes.presentation.adapters.ClickableItemAdapter
import com.example.recipes.presentation.ui.GridListFragment
import com.example.recipes.presentation.ui.cuisinesInteractor
import com.example.recipes.presentation.viewmodels.BaseViewModel
import com.example.recipes.presentation.viewmodels.cuisines.CuisinesViewModelFactory

class CuisinesFragment : GridListFragment<CuisineDomain>() {
    override val viewModel: BaseViewModel<CuisineDomain> by viewModels {
        CuisinesViewModelFactory(cuisinesInteractor)
    }

    override val layoutResId = R.layout.fragment_cuisines

    // todo fix DRY violation
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = view.findViewById<RecyclerView>(R.id.cuisines_recycler_view)
        recyclerView.layoutManager = layoutManager

        adapter = ClickableItemAdapter()
        recyclerView.adapter = adapter

        val progressBar = view.findViewById<ProgressBar>(R.id.cuisines_progress_bar)

        viewModel.uiState.observe(viewLifecycleOwner) {
            when (it) {
                is BaseViewModel.UiState.Loading -> progressBar.visibility = View.VISIBLE
                is BaseViewModel.UiState.Success -> {
                    progressBar.visibility = View.GONE

                    adapter.onItemClicked = { position ->
                        val endpoint = "a=${it.items[position].name}"
                        onListItemClick(PreviewsFragment.newInstance(endpoint))
                    }
                    adapter.reload(it.items)

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
        fun newInstance() = CuisinesFragment()
    }
}
package com.example.recipes.presentation.ui.search

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.recipes.R
import com.example.recipes.domain.entities.CuisineDomain
import com.example.recipes.presentation.adapters.ClickableItemAdapter
import com.example.recipes.presentation.ui.GridListFragment
import com.example.recipes.presentation.ui.cuisinesInteractor
import com.example.recipes.presentation.viewmodels.BaseViewModel
import com.example.recipes.presentation.viewmodels.CuisinesViewModelFactory

class CuisinesFragment : GridListFragment<CuisineDomain>() {
    override val viewModel: BaseViewModel<CuisineDomain> by viewModels {
        CuisinesViewModelFactory(cuisinesInteractor)
    }

    override val layoutResId = R.layout.fragment_cuisines

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = view.findViewById<RecyclerView>(R.id.recycler_view)
        val swipeRefreshLayout = view.findViewById<SwipeRefreshLayout>(R.id.swipe_refresh_layout)
        recyclerView.layoutManager = layoutManager

        adapter = ClickableItemAdapter()
        recyclerView.adapter = adapter

        val reconnectTextView = view.findViewById<TextView>(R.id.reconnect_text_view)

        swipeRefreshLayout.setOnRefreshListener {
            viewModel.reload()
        }

        viewModel.uiState.observe(viewLifecycleOwner) {
            when (it) {
                is BaseViewModel.UiState.Loading -> {
                    recyclerView.visibility = View.GONE
                    swipeRefreshLayout.isRefreshing = true
                }
                is BaseViewModel.UiState.Success -> {
                    swipeRefreshLayout.isRefreshing = false
                    reconnectTextView.visibility = View.GONE
                    adapter.reload(it.items)
                    recyclerView.visibility = View.VISIBLE

                    adapter.onItemClicked = { position ->
                        val endpoint = "a=${it.items[position].name}"
                        onListItemClick(PreviewsFragment.newInstance(endpoint))
                    }
                }
                is BaseViewModel.UiState.Failure -> {
                    swipeRefreshLayout.isRefreshing = false
                    recyclerView.visibility = View.GONE
                    reconnectTextView.visibility = View.VISIBLE
                }
            }
        }

    }

    companion object {
        @JvmStatic
        fun newInstance() = CuisinesFragment()
    }
}
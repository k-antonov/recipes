package com.example.recipes.presentation.cuisines

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.recipes.R
import com.example.recipes.domain.cuisines.CuisineDomain
import com.example.recipes.presentation.core.view.GridListFragment
import com.example.recipes.presentation.previews.PreviewsFragment
import com.example.recipes.presentation.core.viewmodel.BaseViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CuisinesFragment : GridListFragment<CuisineDomain>() {

    override val viewModel: CuisinesViewModel by viewModels()

    override val layoutResId = R.layout.fragment_cuisines

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val swipeRefreshLayout = view.findViewById<SwipeRefreshLayout>(R.id.swipe_refresh_layout)
        val recyclerView = view.findViewById<RecyclerView>(R.id.recycler_view)
        recyclerView.layoutManager = layoutManager

        adapter = CuisinesAdapter()
        recyclerView.adapter = adapter

        val progressBar = view.findViewById<ProgressBar>(R.id.progress_bar)
        val uiFailureTextView = view.findViewById<TextView>(R.id.ui_failure_text_view)
        uiFailureTextView.text = getString(R.string.reconnect)

        swipeRefreshLayout.setOnRefreshListener {
            viewModel.reload()
        }

        viewModel.uiState.observe(viewLifecycleOwner) {
            when (it) {
                is BaseViewModel.UiState.Loading -> {
                }
                is BaseViewModel.UiState.Success -> {
                    adapter.reload(it.items)
                    swipeRefreshLayout.isRefreshing = false
                    recyclerView.visibility = View.VISIBLE
                    progressBar.visibility = View.GONE
                    uiFailureTextView.visibility = View.GONE

                    adapter.onItemClicked = { position ->
                        val endpoint = "a=${it.items[position].name}"
                        onListItemClick(PreviewsFragment.newInstance(endpoint))
                    }
                }
                is BaseViewModel.UiState.Failure -> {
                    swipeRefreshLayout.isRefreshing = false
                    progressBar.visibility = View.GONE
                    recyclerView.visibility = View.GONE
                    uiFailureTextView.visibility = View.VISIBLE
                }
            }
        }

    }

    companion object {
        @JvmStatic
        fun newInstance() = CuisinesFragment()
    }
}
package com.example.recipes.presentation.ui.search

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.recipes.R
import com.example.recipes.domain.entities.PreviewDomain
import com.example.recipes.presentation.adapters.ClickableItemAdapter
import com.example.recipes.presentation.ui.BaseListFragment
import com.example.recipes.presentation.ui.previewsInteractor
import com.example.recipes.presentation.viewmodels.BaseViewModel
import com.example.recipes.presentation.viewmodels.PreviewsViewModelFactory

private const val ARG_ENDPOINT = "endpoint"

class PreviewsFragment : BaseListFragment<PreviewDomain>() {

    private lateinit var endpoint: String

    override val viewModel: BaseViewModel<PreviewDomain> by viewModels {
        PreviewsViewModelFactory(previewsInteractor, endpoint)
    }

    override val layoutResId = R.layout.fragment_preview

    override val layoutManager: RecyclerView.LayoutManager
        get() = LinearLayoutManager(requireContext())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            endpoint = it.getString(ARG_ENDPOINT).toString()
        }
        viewModel.reload()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val swipeRefreshLayout = view.findViewById<SwipeRefreshLayout>(R.id.swipe_refresh_layout)
        val recyclerView = view.findViewById<RecyclerView>(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

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
                        val recipeId = it.items[position].id
                        onListItemClick(DetailsFragment.newInstance(recipeId))
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
        fun newInstance(endpoint: String) =
            PreviewsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_ENDPOINT, endpoint)
                }
            }
    }

}
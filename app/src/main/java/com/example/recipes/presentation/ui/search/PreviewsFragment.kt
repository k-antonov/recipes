package com.example.recipes.presentation.ui.search

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.lifecycle.SavedStateHandle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.recipes.R
import com.example.recipes.domain.entities.PreviewDomain
import com.example.recipes.presentation.adapters.PreviewsAdapter
import com.example.recipes.presentation.ui.BaseListFragment
import com.example.recipes.presentation.viewmodels.BaseViewModel
import com.example.recipes.presentation.viewmodels.PreviewsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PreviewsFragment : BaseListFragment<PreviewDomain>() {

    private lateinit var endpoint: String

    private val savedStateHandle = SavedStateHandle()

    override val viewModel: PreviewsViewModel by viewModels()

    override val layoutResId = R.layout.fragment_previews

    override val layoutManager: RecyclerView.LayoutManager
        get() = LinearLayoutManager(requireContext())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            endpoint = it.getString(ARG_ENDPOINT).toString()
        }
        viewModel.reload()
        savedStateHandle.set(ARG_ENDPOINT, endpoint)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val swipeRefreshLayout = view.findViewById<SwipeRefreshLayout>(R.id.swipe_refresh_layout)
        val recyclerView = view.findViewById<RecyclerView>(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

//        adapter = ClickableItemAdapter()
        adapter = PreviewsAdapter()
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
                    Log.d("PreviewsAdapter", "calling reload!")
                    adapter.reload(it.items)
                    swipeRefreshLayout.isRefreshing = false
                    recyclerView.visibility = View.VISIBLE
                    progressBar.visibility = View.GONE
                    uiFailureTextView.visibility = View.GONE

                    adapter.onItemClicked = { position ->
                        val recipeId = it.items[position].id
                        onListItemClick(DetailsFragment.newInstance(recipeId))
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
        fun newInstance(endpoint: String) =
            PreviewsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_ENDPOINT, endpoint)
                }
            }

        const val ARG_ENDPOINT = "endpoint"
    }

}
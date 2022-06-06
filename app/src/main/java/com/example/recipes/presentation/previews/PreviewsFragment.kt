package com.example.recipes.presentation.previews

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.lifecycle.SavedStateHandle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.recipes.R
import com.example.recipes.domain.previews.PreviewDomain
import com.example.recipes.presentation.core.view.BaseListFragment
import com.example.recipes.presentation.details.DetailsFragment
import com.example.recipes.presentation.core.viewmodel.BaseViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PreviewsFragment : BaseListFragment<PreviewDomain>() {

    private lateinit var endpoint: String

    private val savedStateHandle = SavedStateHandle()

    override val viewModel: PreviewsViewModel by viewModels()

    override val layoutResId = R.layout.fragment_previews

    override val layoutManager: RecyclerView.LayoutManager
        get() = LinearLayoutManager(requireContext())

    private lateinit var bottomNav: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            endpoint = it.getString(ARG_ENDPOINT).toString()
        }
        viewModel.reload()
        savedStateHandle.set(ARG_ENDPOINT, endpoint)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bottomNav = requireActivity().findViewById(R.id.bottom_navigation)
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onStart() {
        super.onStart()
        bottomNav.animate().translationY(0f).duration = 200
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val swipeRefreshLayout = view.findViewById<SwipeRefreshLayout>(R.id.swipe_refresh_layout)
        val recyclerView = view.findViewById<RecyclerView>(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        adapter = PreviewsAdapter { selectedItem ->
            val recipeId = selectedItem.id
            onListItemClick(DetailsFragment.newInstance(recipeId))
        }
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
                    recyclerView.scrollToPosition(0)
                    swipeRefreshLayout.isRefreshing = false
                    recyclerView.visibility = View.VISIBLE
                    progressBar.visibility = View.GONE
                    uiFailureTextView.visibility = View.GONE
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
package com.example.recipes.presentation.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.recipes.R
import com.example.recipes.domain.previews.PreviewDomain
import com.example.recipes.presentation.previews.PreviewsAdapter
import com.example.recipes.presentation.core.view.BaseListFragment
import com.example.recipes.presentation.details.DetailsFragment
import com.example.recipes.presentation.core.viewmodel.BaseViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoritePreviewsFragment : BaseListFragment<PreviewDomain>() {

    override val viewModel: FavoritePreviewsViewModel by viewModels()

    override val layoutResId = R.layout.fragment_favorite_previews

    override val layoutManager: RecyclerView.LayoutManager
        get() = LinearLayoutManager(requireContext())

    private lateinit var bottomNav: BottomNavigationView

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
        viewModel.reload()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = view.findViewById<RecyclerView>(R.id.recycler_view)
        recyclerView.layoutManager = layoutManager

        val progressBar = view.findViewById<ProgressBar>(R.id.progress_bar)
        val uiFailureTextView = view.findViewById<TextView>(R.id.ui_failure_text_view)
        uiFailureTextView.text = getString(R.string.no_favorite_recipes_yet)

        adapter = PreviewsAdapter { selectedItem ->
            val recipeId = selectedItem.id
            onListItemClick(DetailsFragment.newInstance(recipeId))
        }
        recyclerView.adapter = adapter

        viewModel.uiState.observe(viewLifecycleOwner) {
            when (it) {
                is BaseViewModel.UiState.Loading -> {
                }
                is BaseViewModel.UiState.Success -> {
                    adapter.reload(it.items)
                    recyclerView.visibility = View.VISIBLE
                    progressBar.visibility = View.GONE
                    uiFailureTextView.visibility = View.GONE
                }
                is BaseViewModel.UiState.Failure -> {
                    progressBar.visibility = View.GONE
                    recyclerView.visibility = View.GONE
                    uiFailureTextView.visibility = View.VISIBLE
                }
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = FavoritePreviewsFragment()
    }
}
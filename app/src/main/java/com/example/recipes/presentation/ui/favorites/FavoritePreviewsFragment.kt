package com.example.recipes.presentation.ui.favorites

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.recipes.R
import com.example.recipes.domain.entities.PreviewDomain
import com.example.recipes.presentation.adapters.ClickableItemAdapter
import com.example.recipes.presentation.adapters.PreviewsAdapter
import com.example.recipes.presentation.ui.BaseListFragment
import com.example.recipes.presentation.ui.search.DetailsFragment
import com.example.recipes.presentation.viewmodels.BaseViewModel
import com.example.recipes.presentation.viewmodels.FavoritePreviewsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoritePreviewsFragment : BaseListFragment<PreviewDomain>() {

    override val viewModel: FavoritePreviewsViewModel by viewModels()

    override val layoutResId = R.layout.fragment_favorite_previews

    override val layoutManager: RecyclerView.LayoutManager
        get() = LinearLayoutManager(requireContext())

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = view.findViewById<RecyclerView>(R.id.recycler_view)
        recyclerView.layoutManager = layoutManager

        val progressBar = view.findViewById<ProgressBar>(R.id.progress_bar)
        val uiFailureTextView = view.findViewById<TextView>(R.id.ui_failure_text_view)
        uiFailureTextView.text = getString(R.string.no_favorite_recipes_yet)

//        adapter = ClickableItemAdapter()
        adapter = PreviewsAdapter()
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

                    adapter.onItemClicked = { position ->
                        val recipeId = it.items[position].id
                        onListItemClick(DetailsFragment.newInstance(recipeId))
                    }
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
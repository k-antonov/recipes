package com.example.recipes.presentation.ui.search

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
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

        val recyclerView = view.findViewById<RecyclerView>(R.id.cuisines_recycler_view)
        recyclerView.layoutManager = layoutManager

        adapter = ClickableItemAdapter()
        recyclerView.adapter = adapter

        val progressBar = view.findViewById<ProgressBar>(R.id.cuisines_progress_bar)

        val reconnectButton = view.findViewById<Button>(R.id.cuisines_reconnect_button)
        reconnectButton.setOnClickListener {
            viewModel.reload()
        }

        val onDialogPositiveAction = {
            reconnectButton.visibility = View.GONE
            viewModel.reload()
        }

        val onDialogDismissAction = {
            reconnectButton.visibility = View.VISIBLE
        }

        viewModel.uiState.observe(viewLifecycleOwner) {
            when (it) {
                is BaseViewModel.UiState.Loading -> {
                    progressBar.visibility = View.VISIBLE
                    reconnectButton.visibility = View.GONE
                }
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
                    showErrorDialog(
                        it.throwable.message,
                        onPositiveAction = onDialogPositiveAction,
                        onDismissAction = onDialogDismissAction
                    )
                }
            }
        }

    }

    companion object {
        @JvmStatic
        fun newInstance() = CuisinesFragment()
    }
}
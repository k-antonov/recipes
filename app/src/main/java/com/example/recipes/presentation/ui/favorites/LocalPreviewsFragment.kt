package com.example.recipes.presentation.ui.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.recipes.R
import com.example.recipes.domain.entities.PreviewDomain
import com.example.recipes.presentation.adapters.ClickableItemAdapter
import com.example.recipes.presentation.ui.localPreviewsInteractor
import com.example.recipes.presentation.viewmodels.BaseViewModel
import com.example.recipes.presentation.viewmodels.LocalPreviewsViewModelFactory

class LocalPreviewsFragment : Fragment() {

    private val layoutResId = R.layout.fragment_preview

    private val viewModel: BaseViewModel<PreviewDomain> by viewModels {
        LocalPreviewsViewModelFactory(localPreviewsInteractor)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = layoutInflater.inflate(layoutResId, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = view.findViewById<RecyclerView>(R.id.preview_recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        val adapter = ClickableItemAdapter<PreviewDomain>()
        recyclerView.adapter = adapter

        viewModel.uiState.observe(viewLifecycleOwner) {
            when (it) {
                is BaseViewModel.UiState.Loading -> {
                }
                is BaseViewModel.UiState.Success -> {

                    adapter.onItemClicked = { position ->
                        val recipeId = it.items[position].id
                        onListItemClick(LocalDetailsFragment.newInstance(recipeId))
                    }
                    adapter.reload(it.items)
                }
                is BaseViewModel.UiState.Failure -> {
                }
            }
        }

    }

    companion object {
        @JvmStatic
        fun newInstance() = LocalPreviewsFragment()
    }

    // перенести эти два метода (взяты из BaseListFragment)
    protected fun onListItemClick(fragment: Fragment) {
        replaceFragmentWith(fragment)
    }

    private fun replaceFragmentWith(fragment: Fragment) {
        requireActivity().supportFragmentManager
            .beginTransaction()
            .replace(R.id.main_fragment_container, fragment)
            .addToBackStack(null)
            .commit()
    }

}
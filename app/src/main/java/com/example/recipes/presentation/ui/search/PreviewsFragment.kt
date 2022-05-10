package com.example.recipes.presentation.ui.search

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.recipes.R
import com.example.recipes.domain.entities.PreviewDomain
import com.example.recipes.presentation.adapters.ClickableItemAdapter
import com.example.recipes.presentation.ui.BaseListFragment
import com.example.recipes.presentation.ui.previewsInteractor
import com.example.recipes.presentation.viewmodels.BaseViewModel
import com.example.recipes.presentation.viewmodels.previews.PreviewsViewModelFactory

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
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = view.findViewById<RecyclerView>(R.id.preview_recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        viewModel.itemDomainList.observe(viewLifecycleOwner) {
            adapter = ClickableItemAdapter(it) { position ->
                val endpoint = it[position].id
                onListItemClick(DetailsFragment.newInstance(endpoint))
            }
            recyclerView.adapter = adapter
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
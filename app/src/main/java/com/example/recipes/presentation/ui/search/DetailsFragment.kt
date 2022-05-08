package com.example.recipes.presentation.ui.search

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.recipes.R
import com.example.recipes.domain.entities.DetailDomain
import com.example.recipes.presentation.ui.detailsInteractor
import com.example.recipes.presentation.viewmodels.BaseViewModel
import com.example.recipes.presentation.viewmodels.details.DetailsViewModelFactory

private const val ARG_ENDPOINT = "endpoint"

class DetailsFragment : Fragment() {

    private lateinit var endpoint: String

    val viewModel: BaseViewModel<DetailDomain> by viewModels {
        DetailsViewModelFactory(detailsInteractor, endpoint)
    }

    private val layoutResId = R.layout.fragment_details

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            endpoint = it.getString(ARG_ENDPOINT).toString()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = layoutInflater.inflate(layoutResId, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.itemDomainList.observe(viewLifecycleOwner) {
            Log.d("DetailsFragment", "observing $it")
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(endpoint: String) =
            DetailsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_ENDPOINT, endpoint)
                }
            }
    }
}
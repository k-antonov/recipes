package com.example.recipes.presentation.ui.search

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.recipes.R
import com.example.recipes.domain.entities.DetailDomain
import com.example.recipes.presentation.utils.ImageDownloader
import com.example.recipes.presentation.adapters.IngredientsAdapter
import com.example.recipes.presentation.ui.BaseFragment
import com.example.recipes.presentation.ui.detailsInteractor
import com.example.recipes.presentation.viewmodels.BaseViewModel
import com.example.recipes.presentation.viewmodels.DetailsViewModelFactory

private const val ARG_ENDPOINT = "endpoint"

class DetailsFragment : BaseFragment<DetailDomain>(), ErrorDialog {

    private lateinit var endpoint: String

    override val viewModel: BaseViewModel<DetailDomain> by viewModels {
        DetailsViewModelFactory(detailsInteractor, endpoint)
    }

    override val layoutResId = R.layout.fragment_details

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            endpoint = it.getString(ARG_ENDPOINT).toString()
        }
    }

    // todo fix DRY violation
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val layout = view.findViewById<ConstraintLayout>(R.id.details_constraint_layout)
        val image = view.findViewById<ImageView>(R.id.details_image)
        val name = view.findViewById<TextView>(R.id.details_name)
        val category = view.findViewById<TextView>(R.id.details_category_and_cuisine)

        val ingredientsRecyclerView =
            view.findViewById<RecyclerView>(R.id.details_ingredients_recyclerview)
        ingredientsRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        val adapter = IngredientsAdapter()
        ingredientsRecyclerView.adapter = adapter

        val instructions = view.findViewById<TextView>(R.id.details_instructions)

        val progressBar = view.findViewById<ProgressBar>(R.id.details_progress_bar)

        val reconnectButton = view.findViewById<Button>(R.id.details_reconnect_button)
        reconnectButton.setOnClickListener {
            viewModel.reload()
        }

        viewModel.uiState.observe(viewLifecycleOwner) {
            when (it) {
                is BaseViewModel.UiState.Loading -> {
                    progressBar.visibility = View.VISIBLE
                    reconnectButton.visibility = View.GONE
                    layout.visibility = View.GONE
                }
                is BaseViewModel.UiState.Success -> {
                    progressBar.visibility = View.GONE
                    layout.visibility = View.VISIBLE

                    // нужно как-то переписать маппер
                    with(it.items[0]) {
                        ImageDownloader.load(image, imageUrl)
                        name.text = this.name
                        category.text = getString(
                            R.string.string_comma_string_placeholder,
                            nameCategory,
                            nameCuisine
                        )
                        instructions.text = strInstructions

                        adapter.reload(ingredients, measures)
                    }
                }
                is BaseViewModel.UiState.Failure -> {
                    progressBar.visibility = View.INVISIBLE
                    showErrorDialog(it.throwable.message, reconnectButton, requireActivity(), viewModel)
                }
            }
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
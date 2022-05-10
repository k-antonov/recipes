package com.example.recipes.presentation.ui.search

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.recipes.R
import com.example.recipes.domain.entities.DetailDomain
import com.example.recipes.presentation.ImageDownloader
import com.example.recipes.presentation.adapters.IngredientsAdapter
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

        val instructions = view.findViewById<TextView>(R.id.details_instructions)

        val progressBar = view.findViewById<ProgressBar>(R.id.details_progress_bar)

        viewModel.uiState.observe(viewLifecycleOwner) {
            when (it) {
                is BaseViewModel.UiState.Loading -> {
                    progressBar.visibility = View.VISIBLE
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

                        val adapter = IngredientsAdapter(ingredients, measures)
                        ingredientsRecyclerView.adapter = adapter

                        instructions.text = strInstructions
                    }
                }
                else -> Log.d("CategoriesFragment", "error handling")
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
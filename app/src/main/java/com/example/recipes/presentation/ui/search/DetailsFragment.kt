package com.example.recipes.presentation.ui.search

import android.os.Bundle
import android.view.MenuItem
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
import com.example.recipes.presentation.adapters.IngredientsAdapter
import com.example.recipes.presentation.ui.BaseFragment
import com.example.recipes.presentation.ui.detailsInteractor
import com.example.recipes.presentation.utils.ImageDownloader
import com.example.recipes.presentation.viewmodels.BaseViewModel
import com.example.recipes.presentation.viewmodels.DetailsViewModelFactory
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.CollapsingToolbarLayout

private const val ARG_RECIPE_ID = "recipe_id"

class DetailsFragment : BaseFragment<DetailDomain>() {

    private var recipeId: Long = 0

    override val viewModel: BaseViewModel<DetailDomain> by viewModels {
        DetailsViewModelFactory(detailsInteractor, recipeId)
    }

    override val layoutResId = R.layout.fragment_details

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            recipeId = it.getLong(ARG_RECIPE_ID)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val appbar = view.findViewById<AppBarLayout>(R.id.appbar)
        val collapsingToolbar = view.findViewById<CollapsingToolbarLayout>(R.id.collapsing_toolbar)

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

        val onDialogPositiveAction = {
            reconnectButton.visibility = View.GONE
            viewModel.reload()
        }

        val onDialogCancelAction = {
            reconnectButton.visibility = View.VISIBLE
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
                        setTitleWhenCollapsed(appbar, collapsingToolbar, this.name)
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
                    showErrorDialog(
                        it.throwable.message,
                        onPositiveAction = onDialogPositiveAction,
                        onCancelAction = onDialogCancelAction
                    )
                }
            }
        }

    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.action_favorite -> {
            //
            true
        }
        else -> super.onOptionsItemSelected(item)
    }

    private fun setTitleWhenCollapsed(
        appbar: AppBarLayout,
        collapsingToolbar: CollapsingToolbarLayout,
        title: String
    ) {
        var isShow = true
        var scrollRange = -1
        appbar.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { appBarLayout, verticalOffset ->
            if (scrollRange == -1) {
                scrollRange = appBarLayout?.totalScrollRange!!
            }
            when {
                scrollRange + verticalOffset == 0 -> {
                    collapsingToolbar.title = title
                    isShow = true
                }
                isShow -> {
                    collapsingToolbar.title = " "
                    isShow = false
                }
            }
        })
    }

    companion object {
        @JvmStatic
        fun newInstance(recipeId: Long) =
            DetailsFragment().apply {
                arguments = Bundle().apply {
                    putLong(ARG_RECIPE_ID, recipeId)
                }
            }
    }
}
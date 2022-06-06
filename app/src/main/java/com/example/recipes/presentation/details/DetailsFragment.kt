package com.example.recipes.presentation.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.viewModels
import androidx.lifecycle.SavedStateHandle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.recipes.R
import com.example.recipes.domain.details.DetailDomain
import com.example.recipes.presentation.core.view.BaseFragment
import com.example.recipes.presentation.core.ImageDownloader
import com.example.recipes.presentation.core.viewmodel.BaseViewModel
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailsFragment : BaseFragment<DetailDomain>() {

    private var recipeId: Long = 0

    private val savedStateHandle = SavedStateHandle()

    override val viewModel: DetailsViewModel by viewModels()

    override val layoutResId = R.layout.fragment_details

    private lateinit var bottomNav: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            recipeId = it.getLong(ARG_RECIPE_ID)
        }
        savedStateHandle.set(ARG_RECIPE_ID, recipeId)
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
        bottomNav.animate().translationY(bottomNav.height.toFloat()).duration = 200
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val appbar = view.findViewById<AppBarLayout>(R.id.appbar)
        val collapsingToolbar = view.findViewById<CollapsingToolbarLayout>(R.id.collapsing_toolbar)
        val toolbar = view.findViewById<MaterialToolbar>(R.id.toolbar)

        val layout = view.findViewById<ConstraintLayout>(R.id.details_constraint_layout)
        val image = view.findViewById<ImageView>(R.id.details_image)
        val name = view.findViewById<TextView>(R.id.details_name)
        val category = view.findViewById<TextView>(R.id.details_category_and_cuisine)

        val favoriteItem = toolbar.menu.findItem(R.id.action_favorite)

        val ingredientsRecyclerView =
            view.findViewById<RecyclerView>(R.id.details_ingredients_recyclerview)
        ingredientsRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        val adapter = IngredientsAdapter()
        ingredientsRecyclerView.adapter = adapter

        val instructions = view.findViewById<TextView>(R.id.details_instructions)

        val progressBar = view.findViewById<ProgressBar>(R.id.progress_bar)

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

                        favoriteItem.setFavorite(isFavorite)
                        toolbar.setClickListener(id, isFavorite)
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

    private fun MaterialToolbar.setClickListener(recipeId: Long, wasFavorite: Boolean) = run {
        setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.action_favorite -> {
                    viewModel.changeFavoriteStatus(recipeId, !wasFavorite)
                    viewModel.reload()
                    true
                }
                else -> false
            }
        }
    }

    private fun MenuItem.setFavorite(isFavorite: Boolean) {
        val icon = if (isFavorite) R.drawable.ic_favorite else R.drawable.ic_unfavorite
        setIcon(icon)
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

        const val ARG_RECIPE_ID = "recipe_id"
    }
}
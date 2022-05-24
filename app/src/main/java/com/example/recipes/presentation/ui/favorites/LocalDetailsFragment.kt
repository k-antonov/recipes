package com.example.recipes.presentation.ui.favorites

//import android.os.Bundle
//import android.view.View
//import android.widget.ImageView
//import android.widget.TextView
//import androidx.fragment.app.viewModels
//import androidx.recyclerview.widget.LinearLayoutManager
//import androidx.recyclerview.widget.RecyclerView
//import com.example.recipes.R
//import com.example.recipes.domain.entities.DetailDomain
//import com.example.recipes.presentation.adapters.IngredientsAdapter
//import com.example.recipes.presentation.ui.BaseFragment
//import com.example.recipes.presentation.ui.localDetailsInteractor
//import com.example.recipes.presentation.utils.ImageDownloader
//import com.example.recipes.presentation.viewmodels.BaseViewModel
//import com.example.recipes.presentation.viewmodels.LocalDetailsViewModelFactory
//
//private const val ARG_RECIPE_ID = "arg_recipe_id"

//class LocalDetailsFragment : BaseFragment<DetailDomain>() {
//
//    private var recipeId: Long = 0L
//
//    override val layoutResId = R.layout.fragment_details
//
//    override val viewModel: BaseViewModel<DetailDomain> by viewModels {
//        LocalDetailsViewModelFactory(localDetailsInteractor, recipeId)
//    }
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        arguments?.let {
//            recipeId = it.getLong(ARG_RECIPE_ID)
//        }
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
////        val layout = view.findViewById<ConstraintLayout>(R.id.details_constraint_layout)
//        val image = view.findViewById<ImageView>(R.id.details_image)
//        val name = view.findViewById<TextView>(R.id.details_name)
//        val category = view.findViewById<TextView>(R.id.details_category_and_cuisine)
//
//        val ingredientsRecyclerView =
//            view.findViewById<RecyclerView>(R.id.details_ingredients_recyclerview)
//        ingredientsRecyclerView.layoutManager = LinearLayoutManager(requireContext())
//
//        val adapter = IngredientsAdapter()
//        ingredientsRecyclerView.adapter = adapter
//
//        val instructions = view.findViewById<TextView>(R.id.details_instructions)
//
//        viewModel.uiState.observe(viewLifecycleOwner) {
//            when (it) {
//                is BaseViewModel.UiState.Loading -> {
//                }
//                is BaseViewModel.UiState.Success -> {
//                    with(it.items[0]) {
//                        ImageDownloader.load(image, imageUrl)
//                        name.text = this.name
//                        category.text = getString(
//                            R.string.string_comma_string_placeholder,
//                            nameCategory,
//                            nameCuisine
//                        )
//                        instructions.text = strInstructions
//
//                        adapter.reload(ingredients, measures)
//                    }
//                }
//                is BaseViewModel.UiState.Failure -> {
//                }
//            }
//        }
//
//    }
//
//    companion object {
//        @JvmStatic
//        fun newInstance(recipeId: Long) =
//            LocalDetailsFragment().apply {
//                arguments = Bundle().apply {
//                    putLong(ARG_RECIPE_ID, recipeId)
//                }
//            }
//    }
//}
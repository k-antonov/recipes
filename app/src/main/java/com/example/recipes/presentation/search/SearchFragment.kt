package com.example.recipes.presentation.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.recipes.R
import com.example.recipes.databinding.FragmentSearchBinding
import com.example.recipes.presentation.categories.CategoriesFragment
import com.example.recipes.presentation.categories.CategoriesViewModel
import com.example.recipes.presentation.cuisines.CuisinesFragment
import com.example.recipes.presentation.cuisines.CuisinesViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SearchFragment : Fragment() {

    private lateinit var binding: FragmentSearchBinding

    private val cuisinesViewModel: CuisinesViewModel by viewModels()
    private val categoriesViewModel: CategoriesViewModel by viewModels()

    private val childFragments = listOf<Fragment>(
        CategoriesFragment.newInstance(),
        CuisinesFragment.newInstance()
    )

    private lateinit var bottomNav: BottomNavigationView

    @Inject
    lateinit var childFragmentTitles: List<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        categoriesViewModel.reload()
        cuisinesViewModel.reload()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchBinding.inflate(layoutInflater, container, false)

        val adapter = SearchViewPagerAdapter(this, childFragments)
        binding.searchViewPager.adapter = adapter

        TabLayoutMediator(binding.searchTabLayout, binding.searchViewPager) { tabItem, position ->
            tabItem.text = childFragmentTitles[position]
        }.attach()

        bottomNav = requireActivity().findViewById(R.id.bottom_navigation)

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        bottomNav.animate().translationY(0f).duration = 200
    }

    companion object {
        @JvmStatic
        fun newInstance() = SearchFragment()
    }
}
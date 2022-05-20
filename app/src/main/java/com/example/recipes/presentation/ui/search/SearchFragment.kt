package com.example.recipes.presentation.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.recipes.databinding.FragmentSearchBinding
import com.example.recipes.presentation.adapters.SearchViewPagerAdapter
import com.google.android.material.tabs.TabLayoutMediator

class SearchFragment : Fragment() {

    private lateinit var binding: FragmentSearchBinding

    // by lazy?
    private val childFragments = listOf<Fragment>(
        CategoriesFragment.newInstance(),
        CuisinesFragment.newInstance()
    )

    // todo fix crash
    private val res = context?.resources
    private val childFragmentTitles = listOf(
//        res?.getString(R.string.categories),
//        res?.getString(R.string.cuisines)
        "Categories", "Cuisines"
    )

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

        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance() = SearchFragment()
    }
}
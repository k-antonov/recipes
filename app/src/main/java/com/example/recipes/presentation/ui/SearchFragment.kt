package com.example.recipes.presentation.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.recipes.R
import com.example.recipes.databinding.FragmentSearchBinding
import com.example.recipes.presentation.adapters.SearchViewPagerAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class SearchFragment : Fragment() {

    private lateinit var binding: FragmentSearchBinding

    // by lazy?
    private val childFragments = listOf(
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

//        binding.searchTabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
//            override fun onTabSelected(tab: TabLayout.Tab?) {
//                requireActivity().supportFragmentManager
//                    .beginTransaction()
//                    .replace(R.id.search_view_pager, childFragments[tab?.position!!])
//                    .commit()
//            }
//
//            override fun onTabUnselected(tab: TabLayout.Tab?) {
//            }
//
//            override fun onTabReselected(tab: TabLayout.Tab?) {
//            }
//        })

        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance() = SearchFragment()
    }
}
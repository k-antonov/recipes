package com.example.recipes.presentation.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.recipes.databinding.FragmentSearchBinding
import com.example.recipes.presentation.adapters.SearchViewPagerAdapter
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import javax.inject.Named

@AndroidEntryPoint
class SearchFragment : Fragment() {

    private lateinit var binding: FragmentSearchBinding

    private val childFragments = listOf<Fragment>(
        CategoriesFragment.newInstance(),
        CuisinesFragment.newInstance()
    )

    @Inject
    lateinit var childFragmentTitles: List<String>

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
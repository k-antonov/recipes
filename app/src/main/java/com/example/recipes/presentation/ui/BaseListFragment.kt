package com.example.recipes.presentation.ui

import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.recipes.R
import com.example.recipes.presentation.adapters.ClickableItemAdapter

abstract class BaseListFragment<Entity> : BaseFragment<Entity>() {

    protected abstract val layoutManager: RecyclerView.LayoutManager

    protected lateinit var adapter: ClickableItemAdapter<Entity>

    protected fun onListItemClick(fragment: Fragment) {
        replaceFragmentWith(fragment)
    }

    private fun replaceFragmentWith(fragment: Fragment) {
        requireActivity().supportFragmentManager
            .beginTransaction()
            .replace(R.id.main_fragment_container, fragment)
            .addToBackStack(null)
            .commit()
    }

}
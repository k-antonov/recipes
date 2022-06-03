package com.example.recipes.presentation.core.view

import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.recipes.R
import com.example.recipes.presentation.core.adapter.ClickableItemAdapter

abstract class BaseListFragment<Entity> : BaseFragment<Entity>() {

    protected abstract val layoutManager: RecyclerView.LayoutManager

    protected lateinit var adapter: ClickableItemAdapter<Entity>

    protected fun onListItemClick(fragment: Fragment) {
        replaceFragmentWith(fragment)
    }

    private fun replaceFragmentWith(fragment: Fragment) {
        requireActivity().supportFragmentManager
            .beginTransaction()
            .addToBackStack(null)
            .setCustomAnimations(R.anim.slide_in, R.anim.fade_out, R.anim.fade_in, R.anim.slide_out)
            .replace(R.id.main_fragment_container, fragment)
            .commit()
    }

}
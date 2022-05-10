package com.example.recipes.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.recipes.R
import com.example.recipes.presentation.adapters.ClickableItemAdapter
import com.example.recipes.presentation.viewmodels.BaseViewModel

abstract class BaseListFragment<Entity> : Fragment() {

    protected abstract val viewModel: BaseViewModel<Entity>

    protected abstract val layoutResId: Int

    protected abstract val layoutManager: RecyclerView.LayoutManager

    protected lateinit var adapter: ClickableItemAdapter<Entity>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = layoutInflater.inflate(layoutResId, container, false)

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
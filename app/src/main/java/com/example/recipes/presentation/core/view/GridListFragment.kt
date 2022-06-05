package com.example.recipes.presentation.core.view

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

private const val COLUMNS = 2

abstract class GridListFragment<Entity> : BaseListFragment<Entity>() {

    override val layoutManager: RecyclerView.LayoutManager
        get() = GridLayoutManager(requireContext(), COLUMNS)
}
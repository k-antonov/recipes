package com.example.recipes.presentation.ui

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

abstract class GridListFragment<Entity> : BaseListFragment<Entity>() {

    private val COLUMNS = 2

    override val layoutManager: RecyclerView.LayoutManager
        get() = GridLayoutManager(requireContext(), COLUMNS)
}
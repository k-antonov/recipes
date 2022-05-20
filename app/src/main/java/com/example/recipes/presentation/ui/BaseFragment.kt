package com.example.recipes.presentation.ui

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.example.recipes.R
import com.example.recipes.presentation.viewmodels.BaseViewModel

abstract class BaseFragment<Entity> : Fragment() {

    protected abstract val viewModel: BaseViewModel<Entity>

    protected abstract val layoutResId: Int

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = layoutInflater.inflate(layoutResId, container, false)
}
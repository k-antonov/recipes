package com.example.recipes.presentation.ui.search

import android.app.AlertDialog
import android.view.View
import androidx.fragment.app.FragmentActivity
import com.example.recipes.R
import com.example.recipes.presentation.viewmodels.BaseViewModel

interface ErrorDialog {

    fun <T> showErrorDialog(
        message: String?,
        view: View,
        fragmentActivity: FragmentActivity,
        viewModel: BaseViewModel<T>
    ) {
        val alertDialog = fragmentActivity.let {
            val builder = AlertDialog.Builder(it)
            builder.apply {
                setMessage(message)
                setPositiveButton(R.string.try_again) { _, _ ->
                    view.visibility = View.GONE
                    viewModel.reload()
                }
                setOnDismissListener {
                    view.visibility = View.VISIBLE
                }
            }
            builder.create()
        }
        alertDialog.show()
    }
}
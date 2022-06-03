package com.example.recipes.presentation.core.view

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.recipes.R
import com.example.recipes.presentation.core.viewmodel.BaseViewModel

abstract class BaseFragment<Entity> : Fragment() {

    protected abstract val viewModel: BaseViewModel<Entity>

    protected abstract val layoutResId: Int

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = layoutInflater.inflate(layoutResId, container, false)

    private var alertDialog: AlertDialog? = null

    // возможно, не понадобится
    protected fun showErrorDialog(
        message: String?,
        positiveActionTextId: Int = R.string.try_again,
        onPositiveAction: () -> Unit,
        onCancelAction: () -> Unit
    ) {
        if (alertDialog?.isShowing == true) return
        alertDialog = requireActivity().let {
            val builder = AlertDialog.Builder(it)
            builder.apply {
                setMessage(message)
                setPositiveButton(positiveActionTextId) { _, _ ->
                    onPositiveAction()
                }
                setOnCancelListener {
                    onCancelAction()
                }
            }
            builder.create()
        }
        alertDialog?.show()
    }
}
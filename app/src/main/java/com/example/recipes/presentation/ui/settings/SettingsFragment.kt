package com.example.recipes.presentation.ui.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.example.recipes.R
import com.example.recipes.presentation.ui.settingsInteractor

class SettingsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val clearCacheButton = view.findViewById<Button>(R.id.clear_cache_button)
        clearCacheButton.setOnClickListener {
            settingsInteractor.clearCache()
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = SettingsFragment()
    }
}
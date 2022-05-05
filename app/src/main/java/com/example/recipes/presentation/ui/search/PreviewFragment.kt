package com.example.recipes.presentation.ui.search

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.recipes.R
import com.example.recipes.databinding.FragmentPreviewBinding
import com.example.recipes.domain.entities.PreviewDomain
import com.example.recipes.presentation.adapters.PreviewAdapter
import com.example.recipes.presentation.ui.previewsInteractor
import com.example.recipes.presentation.viewmodels.previews.PreviewsViewModel
import com.example.recipes.presentation.viewmodels.previews.PreviewsViewModelFactory

private const val ARG_ENDPOINT = "endpoint"

class PreviewFragment : Fragment() {
    private lateinit var binding: FragmentPreviewBinding
    private lateinit var adapter: PreviewAdapter<PreviewDomain>
    private lateinit var endpoint: String

    private val viewModel: PreviewsViewModel by viewModels {
        PreviewsViewModelFactory(previewsInteractor, endpoint)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            endpoint = it.getString(ARG_ENDPOINT).toString()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPreviewBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.previewRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        viewModel.previewDomainList.observe(viewLifecycleOwner) {
            adapter = PreviewAdapter(it) {}
            binding.previewRecyclerView.adapter = adapter
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(endpoint: String) =
            PreviewFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_ENDPOINT, endpoint)
                }
            }
    }
}
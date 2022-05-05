package com.example.recipes.presentation.ui.search

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.example.recipes.R
import com.example.recipes.databinding.FragmentCuisinesBinding
import com.example.recipes.domain.entities.CuisineDomain
import com.example.recipes.presentation.adapters.PreviewAdapter
import com.example.recipes.presentation.ui.cuisinesInteractor
import com.example.recipes.presentation.viewmodels.cuisines.CuisinesViewModel
import com.example.recipes.presentation.viewmodels.cuisines.CuisinesViewModelFactory

private const val COLUMNS = 2

// todo fix DRY violation
class CuisinesFragment : Fragment() {

    private lateinit var binding: FragmentCuisinesBinding
    private lateinit var adapter: PreviewAdapter<CuisineDomain>

    private val viewModel: CuisinesViewModel by viewModels {
        CuisinesViewModelFactory(cuisinesInteractor)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCuisinesBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.cuisinesRecyclerView.layoutManager = GridLayoutManager(requireContext(), COLUMNS)

        viewModel.cuisineDomainList.observe(viewLifecycleOwner) {
            adapter = PreviewAdapter(it) { position ->
                val endpoint = "a=${it[position].title}"
                Log.d("CuisinesFragment", endpoint)
                onListItemClick(endpoint)
            }
            binding.cuisinesRecyclerView.adapter = adapter
        }
    }

    private fun onListItemClick(endpoint: String) {
        val fragment = PreviewFragment.newInstance(endpoint)
        replaceFragmentWith(fragment)
    }

    private fun replaceFragmentWith(fragment: Fragment) {
        requireActivity().supportFragmentManager
            .beginTransaction()
            .replace(R.id.main_fragment_container, fragment)
            .addToBackStack(null)
            .commit()
    }

    companion object {
        @JvmStatic
        fun newInstance() = CuisinesFragment()
    }
}
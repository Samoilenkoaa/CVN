package com.bignerdranch.android.cvn.ui.main.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.bignerdranch.android.cvn.data.CountryCovidInfo
import com.bignerdranch.android.cvn.databinding.FavoriteFragmentBinding
import com.bignerdranch.android.cvn.ui.main.adaptercountries.CountriesAdapter
import com.bignerdranch.android.cvn.ui.main.adaptercountries.CountriesAdapterListener
import com.bignerdranch.android.cvn.ui.main.viewmodel.FavoriteViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteFragment : Fragment(), CountriesAdapterListener {
    private var _binding: FavoriteFragmentBinding? = null
    private val binding get() = _binding!!
    private val viewModel: FavoriteViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FavoriteFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.refreshCountries()
        viewModel.favoriteCountriesLiveData.observe(viewLifecycleOwner) { countries ->
            val adapter = CountriesAdapter(countries, this)
            binding.recyclerViewFavorite.adapter = adapter
            val itemTouchHelper = ItemTouchHelper(simpleItemTouchCallback)
            itemTouchHelper.attachToRecyclerView(binding.recyclerViewFavorite)
        }
        viewModel.isLoadingLiveData.observe(viewLifecycleOwner) { isLoading ->
            if (isLoading == true)
                showProgressBar()
            else
                hideProgressBar()
        }

    }

    override fun onCountryItemClick(country: CountryCovidInfo) {
        val action = FavoriteFragmentDirections.actionFavoriteFragment2ToDetailsFragment(country)
        findNavController().navigate(action)
    }

    var simpleItemTouchCallback: ItemTouchHelper.SimpleCallback = object :
        ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT or ItemTouchHelper.DOWN or ItemTouchHelper.UP) {
        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
            return false
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, swipeDir: Int) {
            val position = viewHolder.adapterPosition
            viewModel.deleteFavorite(position)
        }
    }

    fun hideProgressBar() {
        binding.progressBarFavorite.visibility = View.GONE
    }

    fun showProgressBar() {
        binding.progressBarFavorite.visibility = View.VISIBLE
    }
}
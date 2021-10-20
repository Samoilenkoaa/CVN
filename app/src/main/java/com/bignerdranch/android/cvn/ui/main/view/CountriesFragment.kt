package com.bignerdranch.android.cvn.ui.main.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bignerdranch.android.cvn.data.CountryCovidInfo
import com.bignerdranch.android.cvn.databinding.CountriesFragmentBinding
import com.bignerdranch.android.cvn.ui.main.adaptercountries.CountriesAdapter
import com.bignerdranch.android.cvn.ui.main.adaptercountries.CountriesAdapterListener
import com.bignerdranch.android.cvn.ui.main.viewmodel.CountriesViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CountriesFragment : Fragment(), CountriesAdapterListener {
    private var _binding: CountriesFragmentBinding? = null
    private val binding get() = _binding!!
    private val viewModel: CountriesViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setObservers()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = CountriesFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.onRefreshButtonClick()

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                viewModel.filterLiveData.value = newText
                return false
            }
        })
    }

    private fun setObservers() {
        viewModel.countriesLiveData.observe(this) { countries ->
            if (countries != null)
                showData(countries)
        }

        viewModel.isLoadingLiveData.observe(this) { isLoading ->
            if (isLoading == true)
                showProgressBar()
            else
                hideProgressBar()
        }
    }

    fun hideProgressBar() {
        binding.progressBar.visibility = View.GONE
    }

    fun showProgressBar() {
        binding.progressBar.visibility = View.VISIBLE
    }

    fun showData(countries: List<CountryCovidInfo>) {
        val adapter = CountriesAdapter(countries, this)
        binding.recyclerView.adapter = adapter
    }


    override fun onCountryItemClick(country: CountryCovidInfo) {
        val action = HomeFragmentDirections.actionHomeFragment2ToDetailsFragment(country)
        findNavController().navigate(action)
    }


    companion object {
        fun newInstance() = CountriesFragment()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
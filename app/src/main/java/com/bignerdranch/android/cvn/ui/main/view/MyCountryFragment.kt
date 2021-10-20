package com.bignerdranch.android.cvn.ui.main.view


import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bignerdranch.android.cvn.R
import com.bignerdranch.android.cvn.data.CountryCovidInfo
import com.bignerdranch.android.cvn.databinding.MyCountryFragmentBinding
import com.bignerdranch.android.cvn.ui.main.viewmodel.MyCountryViewModel
import com.bignerdranch.android.cvn.ui.util.graphSetting
import com.jjoe64.graphview.series.DataPoint
import dagger.hilt.android.AndroidEntryPoint
import java.text.NumberFormat

@AndroidEntryPoint
class MyCountryFragment : Fragment() {
    private var _binding: MyCountryFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var sharedPreference: SharedPreferences
    private val viewModel: MyCountryViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = MyCountryFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setObservers()
        viewModel.refreshCountriesForSpinner()
        sharedPreference = requireActivity().getSharedPreferences("country", Context.MODE_PRIVATE)

        binding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                p0: AdapterView<*>?,
                p1: View?,
                itemPosition: Int,
                p3: Long
            ) {
                val countries = viewModel.countriesForSpinnerLiveData.value
                if (countries != null) {
                    val country = countries[itemPosition]
                    saveMyCountryName(country.name)
                    viewModel.onSelectedCountryChange(country)
                }
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
        }
    }

    private fun selectMyCountyInSpinner() {
        val myCountryName = sharedPreference.getString("country", "")
        if (!TextUtils.isEmpty(myCountryName)) {
            val countries = viewModel.countriesForSpinnerLiveData.value
            if (countries != null) {
                for (i in countries.indices) {
                    if (countries[i].name == myCountryName) {
                        binding.spinner.setSelection(i)
                    }
                }
            }
        }
    }

    private fun saveMyCountryName(name: String) {
        val editor = sharedPreference.edit()
        editor.putString("country", name)
        editor.apply()
    }

    private fun setObservers() {
        viewModel.countriesForSpinnerLiveData.observe(viewLifecycleOwner) { loadingResult ->
            if (loadingResult != null)
                fillSpinnerWithCountries(loadingResult)
        }

        viewModel.historySelectedCountryLiveData.observe(viewLifecycleOwner) {
            showCountry(it)
        }
    }

    private fun showCountry(country: MyCountryViewModel.MyCountryHistoryInfo) {
        binding.tvAllInfected.text = NumberFormat.getInstance().format(country.today.cases)
        binding.tvDeath.text = NumberFormat.getInstance().format(country.today.deaths)
        binding.tvRecoveredAses.text = NumberFormat.getInstance().format(country.today.recovered)
        showGraph(country)
        showHistoryGraphCases(country)
        showHistoryGraphRecovered(country)
        showHistoryGraphDeaths(country)
    }

    private fun fillSpinnerWithCountries(countries: List<CountryCovidInfo>) {
        val countriesNames = countries.map { it.name }
        val adapter =
            ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, countriesNames)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinner.adapter = adapter
        selectMyCountyInSpinner()

    }

    fun showGraph(country: MyCountryViewModel.MyCountryHistoryInfo) {
        binding.graph.removeAllSeries()
        val array = arrayOf(
            DataPoint(1.0, country.today.cases.toDouble()),
            DataPoint(2.0, country.today.deaths.toDouble()),
            DataPoint(3.0, country.today.recovered.toDouble()),
        )
        graphSetting(
            array,
            arrayOf(
                getString(R.string.Infestations),
                getString(R.string.died),
                getString(R.string.recovered)
            ),
            binding.graph,
            getString(
                R.string.generalStatistics
            )
        )
    }


    fun showHistoryGraphCases(country: MyCountryViewModel.MyCountryHistoryInfo) {
        binding.graphCases.removeAllSeries()
        val array = arrayOf(
            DataPoint(1.0, country.today.cases.toDouble()),
            DataPoint(2.0, country.yesterday.cases.toDouble()),
            DataPoint(3.0, country.twoDaysAgo.cases.toDouble()),
            )
        graphSetting(
            array, arrayOf(
                getString(R.string.today), getString(R.string.yesterday), getString(
                    R.string.twoDaysAgo
                )
            ), binding.graphCases, getString(
                R.string.totalCases
            )
        )
    }

    fun showHistoryGraphRecovered(country: MyCountryViewModel.MyCountryHistoryInfo) {
        binding.graphRecovered.removeAllSeries()
        val array = arrayOf(
            DataPoint(1.0, country.today.recovered.toDouble()),
            DataPoint(2.0, country.yesterday.recovered.toDouble()),
            DataPoint(3.0, country.twoDaysAgo.recovered.toDouble()),
            )
        graphSetting(
            array, arrayOf(
                getString(R.string.today), getString(R.string.yesterday), getString(
                    R.string.twoDaysAgo
                )
            ), binding.graphRecovered, getString(
                R.string.totalRecovered
            )
        )
    }

    fun showHistoryGraphDeaths(country: MyCountryViewModel.MyCountryHistoryInfo) {
        binding.graphDeaths.removeAllSeries()
        val array = arrayOf(
            DataPoint(1.0, country.today.deaths.toDouble()),
            DataPoint(2.0, country.yesterday.deaths.toDouble()),
            DataPoint(3.0, country.twoDaysAgo.deaths.toDouble()),
            )
        graphSetting(
            array, arrayOf(
                getString(R.string.today), getString(R.string.yesterday), getString(
                    R.string.twoDaysAgo
                )
            ), binding.graphDeaths, getString(
                R.string.totalDied
            )
        )
    }
}
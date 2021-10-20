package com.bignerdranch.android.cvn.ui.main.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bignerdranch.android.cvn.R
import com.bignerdranch.android.cvn.data.WorldCovidInfo
import com.bignerdranch.android.cvn.databinding.WorldFragmentBinding
import com.bignerdranch.android.cvn.ui.main.viewmodel.WorldHistoryInfo
import com.bignerdranch.android.cvn.ui.main.viewmodel.WorldViewModel
import com.bignerdranch.android.cvn.ui.util.graphSetting
import com.jjoe64.graphview.series.DataPoint
import dagger.hilt.android.AndroidEntryPoint
import java.text.NumberFormat

@AndroidEntryPoint
class WorldFragment : Fragment() {
    private var _binding: WorldFragmentBinding? = null
    private val binding get() = _binding!!
    private val viewModel: WorldViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = WorldFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        super.onCreate(savedInstanceState)
        setObserversAll()
        viewModel.onRefreshButtonClickAll()
    }

    fun setObserversAll() {
        viewModel.worldInfoLiveData.observe(viewLifecycleOwner) { worldInfo ->
            Log.d("myLog", worldInfo.toString())
            showWorldInfoAll(worldInfo)
        }

        viewModel.historyLiveData.observe(viewLifecycleOwner) { history ->
            showHistoryGraphCases(history)
            showHistoryGraphRecovered(history)
            showHistoryGraphDeaths(history)
        }

        viewModel.errorLiveData.observe(viewLifecycleOwner) { error ->
            showError(error)
        }

        viewModel.isLoadingLiveData.observe(viewLifecycleOwner) { isLoading ->
            showLoadingProgress(isLoading)
        }
    }

    private fun showLoadingProgress(isLoading: Boolean) {
        if (isLoading) {
            binding.mainLayout.visibility = View.GONE
            binding.progressBarLoading.visibility = View.VISIBLE
        } else {
            binding.progressBarLoading.visibility = View.GONE
            binding.mainLayout.visibility = View.VISIBLE
        }
    }

    private fun showWorldInfoAll(worldCovidInfo: WorldCovidInfo) {
        binding.tvAllInfected.text = NumberFormat.getInstance().format(worldCovidInfo.cases)
        binding.tvDeath.text = NumberFormat.getInstance().format(worldCovidInfo.deaths)
        binding.tvRecovered.text = NumberFormat.getInstance().format(worldCovidInfo.recovered)
        showGraph(worldCovidInfo)
    }

    fun showError(e: Exception) {
        binding.tvAllInfected.text = "status: error ($e)"
    }

    fun showGraph(worldCovidInfo: WorldCovidInfo) {
        val array = arrayOf(
            DataPoint(1.0, worldCovidInfo.cases),
            DataPoint(2.0, worldCovidInfo.deaths),
            DataPoint(3.0, worldCovidInfo.recovered),
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

    fun showHistoryGraphCases(worldHistoryInfo: WorldHistoryInfo) {
        val array = arrayOf(
            DataPoint(1.0, worldHistoryInfo.today.cases),
            DataPoint(2.0, worldHistoryInfo.yesterday.cases),
            DataPoint(3.0, worldHistoryInfo.twoDaysAgo.cases),
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

    fun showHistoryGraphRecovered(worldHistoryInfo: WorldHistoryInfo) {
        val array = arrayOf(
            DataPoint(1.0, worldHistoryInfo.today.recovered),
            DataPoint(2.0, worldHistoryInfo.yesterday.recovered),
            DataPoint(3.0, worldHistoryInfo.twoDaysAgo.recovered),
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

    fun showHistoryGraphDeaths(worldHistoryInfo: WorldHistoryInfo) {
        val array = arrayOf(
            DataPoint(1.0, worldHistoryInfo.today.deaths),
            DataPoint(2.0, worldHistoryInfo.yesterday.deaths),
            DataPoint(3.0, worldHistoryInfo.twoDaysAgo.deaths),
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
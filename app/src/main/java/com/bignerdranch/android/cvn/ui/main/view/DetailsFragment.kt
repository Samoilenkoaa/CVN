package com.bignerdranch.android.cvn.ui.main.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.bignerdranch.android.cvn.databinding.DetailsFragmentBinding
import com.bignerdranch.android.cvn.ui.main.viewmodel.DetailsViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.text.NumberFormat

@AndroidEntryPoint
class DetailsFragment : Fragment() {
    private var _binding: DetailsFragmentBinding? = null
    private val binding get() = _binding!!
    private val viewModel: DetailsViewModel by viewModels()
    private val args: DetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DetailsFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val country = args.country
        binding.tvCountry.text = country.name
        binding.tvCases.text = NumberFormat.getInstance().format(country.cases)
        binding.tvDeaths.text = NumberFormat.getInstance().format(country.deaths)
        binding.tvRecovered.text = NumberFormat.getInstance().format(country.recovered)
        binding.buttonInsertFavoriteFragment.setOnClickListener {
            viewModel.addToFavorite(country)

        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
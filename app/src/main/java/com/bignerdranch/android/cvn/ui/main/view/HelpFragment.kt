package com.bignerdranch.android.cvn.ui.main.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.bignerdranch.android.cvn.R
import com.bignerdranch.android.cvn.databinding.HelpFragmentBinding

class HelpFragment : Fragment() {
    private var _binding: HelpFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = HelpFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.imageViewCovid.setImageDrawable(
            ContextCompat.getDrawable(requireContext(), R.drawable.logocorona)
        )
    }

}
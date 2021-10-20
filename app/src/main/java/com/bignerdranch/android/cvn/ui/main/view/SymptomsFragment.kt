package com.bignerdranch.android.cvn.ui.main.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bignerdranch.android.cvn.databinding.FragmentSymptomsBinding

class SymptomsFragment : Fragment() {
    private var _binding: FragmentSymptomsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSymptomsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.buttonGoHelpTab.setOnClickListener {
            val action = SymptomsFragmentDirections.actionSymptomsFragment2ToHelpFragment2()
            findNavController().navigate(action)
        }
    }
}
package com.bignerdranch.android.cvn.ui.main.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.bignerdranch.android.cvn.R
import com.bignerdranch.android.cvn.databinding.HomeFragmentBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private var _binding: HomeFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = HomeFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewPager.adapter = HomeAdapter(childFragmentManager, requireContext())
        binding.tabLayout.setupWithViewPager(binding.viewPager)
    }
}

class HomeAdapter(fm: FragmentManager, val context: Context) :
    FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    companion object {
        const val TABS_COUNT = 3
    }

    override fun getCount(): Int {
        return TABS_COUNT
    }

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> WorldFragment()
            1 -> MyCountryFragment()
            2 -> CountriesFragment()
            else -> throw Exception("Position not found")
        }
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            0 -> context.getString(R.string.all)
            1 -> context.getString(R.string.my_country)
            2 -> context.getString(R.string.countries_list)
            else -> throw Exception("Position not found")
        }
    }

}
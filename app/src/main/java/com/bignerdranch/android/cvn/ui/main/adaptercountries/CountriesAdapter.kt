package com.bignerdranch.android.cvn.ui.main.adaptercountries

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bignerdranch.android.cvn.R
import com.bignerdranch.android.cvn.data.CountryCovidInfo
import com.bignerdranch.android.cvn.databinding.CountryItemCardBinding
import com.bumptech.glide.Glide
import java.text.NumberFormat

class CountriesAdapter(
    private var countries: List<CountryCovidInfo>,
    val listener: CountriesAdapterListener?
) : RecyclerView.Adapter<CountryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.country_item_card, parent, false)
        return CountryViewHolder(view)
    }

    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {
        val countryCovidInfo = countries[position]
        holder.bind(countryCovidInfo)
        holder.itemView.setOnClickListener {
            listener?.onCountryItemClick(countryCovidInfo)
        }
    }
    override fun getItemCount(): Int {
        return countries.size
    }

}

class CountryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    val binding: CountryItemCardBinding = CountryItemCardBinding.bind(itemView)
    fun bind(countryCovidInfo: CountryCovidInfo) {
        binding.countryNameTextView.text = countryCovidInfo.name
        binding.casesTextView.text = NumberFormat.getInstance().format(countryCovidInfo.cases)
        Glide
            .with(itemView.context)
            .load(countryCovidInfo.countryInfo?.flag)
            .centerCrop()
            .into(binding.flagCountry)
    }
}
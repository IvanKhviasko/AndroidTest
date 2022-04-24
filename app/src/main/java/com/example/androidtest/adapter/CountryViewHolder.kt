package com.example.androidtest.adapter

import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.androidtest.databinding.ItemCountryBinding
import com.example.androidtest.model.Country


class CountryViewHolder(
    private val binding: ItemCountryBinding,
    private val onCountryClicked: (Country) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(country: Country) {
        with(binding) {
            image.load(country.flag) {
            }
            textName.text = country.name
            root.setOnClickListener {
                onCountryClicked(country)
            }
        }
    }
}
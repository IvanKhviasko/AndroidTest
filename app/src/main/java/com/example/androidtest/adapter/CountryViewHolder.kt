package com.example.androidtest.adapter

import androidx.recyclerview.widget.RecyclerView
import com.example.androidtest.databinding.ItemCountryBinding
import com.example.androidtest.model.Country

class CountryViewHolder(
    private val binding: ItemCountryBinding,
    private val onClick: (Country, Int) -> Unit,
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(country: Country) {
        binding.textView.text = country.toString()
        binding.root.setOnLongClickListener {
            onClick(country, BUTTON_DELETE)
            true
        }
        binding.btnEdit.setOnClickListener {
            onClick(country, BUTTON_EDIT)
        }
    }

    companion object {
        const val BUTTON_DELETE = 0
        const val BUTTON_EDIT = 1
    }
}
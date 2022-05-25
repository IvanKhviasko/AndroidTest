package com.example.androidtest.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.androidtest.databinding.ItemCountryBinding
import com.example.androidtest.model.Country
import java.util.*

class CountryAdapter(
    private val context: Context,
    private val onClick: (Country, Int) -> Unit,
) : ListAdapter<Country, CountryViewHolder>(DIFF_UTIL), Filterable {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryViewHolder {
        return CountryViewHolder(
            binding = ItemCountryBinding.inflate(
                LayoutInflater.from(context),
                parent,
                false
            ),
            onClick = onClick,
        )
    }

    override fun getItemCount(): Int {
        return currentList.size
    }

    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object {
        val DIFF_UTIL = object : DiffUtil.ItemCallback<Country>() {
            override fun areItemsTheSame(oldItem: Country, newItem: Country): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Country, newItem: Country): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val filteredList = mutableListOf<Country>()
                if (constraint == null || constraint.isEmpty()) {
                    filteredList.addAll(currentList)
                } else {
                    for (country in currentList) {
                        if (country.toString().lowercase(Locale.getDefault())
                                .startsWith(constraint.toString().lowercase(Locale.getDefault()))
                        ) {
                            filteredList.add(country)
                        }
                    }
                }
                val filterResult = FilterResults()
                filterResult.values = filteredList
                return filterResult
            }

            override fun publishResults(constraint: CharSequence?, result: FilterResults?) {
                submitList(result?.values as MutableList<Country>)
            }
        }
    }
}
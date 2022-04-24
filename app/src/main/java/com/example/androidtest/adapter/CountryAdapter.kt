package com.example.androidtest.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.androidtest.databinding.ItemLoadingBinding
import com.example.androidtest.databinding.ItemCountryBinding
import com.example.androidtest.model.Country
import com.example.androidtest.model.PagingData

class CountryAdapter(
    private val onCountryClicked: (Country) -> Unit
) : ListAdapter<PagingData<Country>, RecyclerView.ViewHolder>(DIFF_CALLBACK) {

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is PagingData.Content -> TYPE_COUNTRY
            PagingData.Loading -> TYPE_LOADING
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            TYPE_COUNTRY -> {
                CountryViewHolder(
                    binding = ItemCountryBinding.inflate(layoutInflater, parent, false),
                    onCountryClicked = onCountryClicked
                )
            }
            TYPE_LOADING -> {
                LoadingViewHolder(
                    binding = ItemLoadingBinding.inflate(layoutInflater, parent, false)
                )
            }
            else -> error("Unsupported viewType $viewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val country = (getItem(position) as? PagingData.Content)?.data ?: return
        (holder as? CountryViewHolder)?.bind(country)
    }

    companion object {

        private const val TYPE_COUNTRY = 1
        private const val TYPE_LOADING = 2

        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<PagingData<Country>>() {
            override fun areItemsTheSame(
                oldItem: PagingData<Country>,
                newItem: PagingData<Country>
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: PagingData<Country>,
                newItem: PagingData<Country>
            ): Boolean {
                val oldCountry = oldItem as? PagingData.Content
                val newCountry = newItem as? PagingData.Content
                return oldCountry?.data == newCountry?.data
            }
        }
    }
}
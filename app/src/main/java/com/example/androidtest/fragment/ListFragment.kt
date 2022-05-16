package com.example.androidtest.fragment

import android.app.SearchManager
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.androidtest.R
import com.example.androidtest.Repositories
import com.example.androidtest.adapter.CountryAdapter
import com.example.androidtest.adapter.CountryViewHolder
import com.example.androidtest.databinding.FragmentListBinding
import com.example.androidtest.dialog.DialogDelete
import com.example.androidtest.dialog.DialogUpdate
import com.example.androidtest.model.Country

class ListFragment : Fragment() {

    private var _binding: FragmentListBinding? = null
    private val binding get() = requireNotNull(_binding)
    private val countryRepository by lazy {
        Repositories.COUNTRY_REPOSITORY
    }

    private val adapter by lazy {
        CountryAdapter(requireContext()) { country, btn ->
            when (btn) {
                CountryViewHolder.BUTTON_DELETE -> {
                    DialogDelete().show(parentFragmentManager, DialogDelete.TAG)
                    dialogDeleteListener(country)
                }
                CountryViewHolder.BUTTON_EDIT -> {
                    DialogUpdate(country).show(parentFragmentManager, DialogUpdate.TAG)
                    dialogUpdateListener(country)
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        return FragmentListBinding.inflate(inflater, container, false)
            .also { _binding = it }
            .root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setHasOptionsMenu(true)
        (activity as AppCompatActivity).setSupportActionBar(binding.toolbar)

        val linearLayout = LinearLayoutManager(requireContext())

        with(binding) {
            recyclerView.layoutManager = linearLayout
            recyclerView.adapter = adapter

        }

        rebuildCountryList()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.toolbar_menu, menu)
        val manager = activity?.getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchItem = menu.findItem(R.id.search)
        val searchView = searchItem.actionView as SearchView

        searchView.setSearchableInfo(manager.getSearchableInfo(activity?.componentName))
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(text: String?): Boolean {
                adapter.filter.filter(text)
                return false
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun rebuildCountryList() {
        val countryList = countryRepository.getAll()
        adapter.submitList(countryList)
    }

    private fun deleteCountry(country: Country) {
        countryRepository.deleteCountry(country).run {
            showToast("$country deleted")
        }
    }

    private fun dialogDeleteListener(country: Country) {
        parentFragmentManager.setFragmentResultListener(
            DialogDelete.REQUEST_KEY,
            this
        ) { _, result ->
            val which = result.getInt(DialogDelete.KEY_RESPONSE)
            if (which == DialogInterface.BUTTON_POSITIVE) {
                deleteCountry(country)
                rebuildCountryList()
            }
        }
    }

    private fun dialogUpdateListener(oldCountry: Country) {
        parentFragmentManager.setFragmentResultListener(
            DialogUpdate.REQUEST_KEY,
            this
        ) { _, result ->
            val id = oldCountry.id
            val countryName = result.getString(DialogUpdate.KEY_COUNTRY_NAME)
            val capitalName = result.getString(DialogUpdate.KEY_CAPITAL_NAME)


            val newCountry = Country(
                id = id,
                countryName = countryName ?: oldCountry.countryName,
                capitalName = capitalName ?: oldCountry.capitalName,
            )
            println(countryName)
            println(capitalName)
            countryRepository.updateCountry(oldCountry, newCountry)

            binding.recyclerView.adapter = adapter
            rebuildCountryList()
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT)
            .show()
    }
}
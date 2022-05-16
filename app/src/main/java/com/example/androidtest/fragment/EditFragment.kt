package com.example.androidtest.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.androidtest.Repositories
import com.example.androidtest.databinding.FragmentMenuBinding
import com.example.androidtest.model.Country

class EditFragment : Fragment() {

    private var _binding: FragmentMenuBinding? = null
    private val binding get() = requireNotNull(_binding)
    private val countryRepository by lazy {
        Repositories.COUNTRY_REPOSITORY
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentMenuBinding.inflate(inflater, container, false)
            .also { _binding = it }
            .root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            btnAdd.setOnClickListener {
                if (!edCountryName.text.isNullOrBlank() && !edCapitalName.text.isNullOrBlank()) {
                    val countryName = edCountryName.text.toString().trim()
                    val capitalName = edCapitalName.text.toString().trim()

                    val country = Country(
                        countryName = countryName,
                        capitalName = capitalName,
                    )
                    countryRepository.insertCountry(country)
                    edCountryName.setText("")
                    edCapitalName.setText("")

                    showToast("Country $countryName $capitalName added")
                } else {
                    showToast("Incorrect values")
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT)
            .show()

    }
}
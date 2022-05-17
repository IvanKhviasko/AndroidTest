package com.example.androidtest.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.setupWithNavController
import coil.load
import com.example.androidtest.databinding.FragmentDetailsBinding
import com.example.androidtest.model.CountryDetails
import com.example.androidtest.retrofit.RetrofitService
import com.google.android.material.snackbar.Snackbar
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailsFragment : Fragment() {

    private var _binding: FragmentDetailsBinding? = null
    private val binding
        get() = requireNotNull(_binding) {
            "View was destroyed"
        }

    private val args: DetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentDetailsBinding.inflate(inflater, container, false)
            .also { binding ->
                _binding = binding
            }
            .root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadCountryDetails()

        with(binding) {
            toolbar.setupWithNavController(findNavController())
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun loadCountryDetails() {
        RetrofitService.countryApi.getCountryDetails(args.name)
            .enqueue(object : Callback <CountryDetails> {
                override fun onResponse(
                    call: Call<CountryDetails>,
                    response: Response<CountryDetails>
                ) {
                    if (response.isSuccessful) {
                        val one = response.body()
                        val country = one!!.countries[0]
                        with(binding) {
                            flag.load(country.flag)
                            name.text = country.name
                            area.text = "Area: ${country.area}"
                            population.text = "Population: ${country.population}"
                        }
                    } else {
                        handleErrors(response.errorBody()?.string() ?: "")
                    }
                }

                override fun onFailure(call: Call <CountryDetails>, t: Throwable) {
                    handleErrors(t.message ?: "")
                }
            })
    }


    private fun handleErrors(errorMessage: String) {
        Snackbar.make(binding.root, errorMessage, Snackbar.LENGTH_SHORT)
            .setAction(android.R.string.ok) {}
            .show()
    }
}
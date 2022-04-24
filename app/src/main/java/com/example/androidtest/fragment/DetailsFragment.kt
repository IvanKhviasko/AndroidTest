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
import com.example.androidtest.model.Countries
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

    val args: DetailsFragmentArgs by navArgs()

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
//        val countriesLoad = RetrofitService.CountryApi.getCountryDetails(args.name)
//        val countryOne= countriesLoad[0]

        //Тут приходит массив с одним значением я не могу его правильно вставить в фрагмент
        RetrofitService.CountryApi.getCountryDetails(args.name)
            .enqueue(object : Callback <List<Countries>> {
                override fun onResponse(
                    call: Call <List<Countries>>,
                    response: Response <List<Countries>>
                ) {
                    if (response.isSuccessful) {
                        val newList = response.body() ?: return
                        with(binding) {
                            flag.load(newList[0].flag)
                            name.text = newList[0].name
                            area.text = "Area: ${newList[0].area}"
                            population.text = "Population: ${newList[0].population}"
                        }
                    } else {
                        handleErrors(response.errorBody()?.string() ?: "")
                    }
                }

                override fun onFailure(call: Call <List<Countries>>, t: Throwable) {
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

private fun <T> Call<T>.enqueue(callback: Callback<List<Countries>>) {//???

}




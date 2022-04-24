package com.example.androidtest.fragment
import addPaginationScrollListener
import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.androidtest.adapter.CountryAdapter
import com.example.androidtest.databinding.FragmentListBinding
import com.example.androidtest.model.Country
import com.example.androidtest.model.PagingData
import com.example.androidtest.retrofit.RetrofitService
import com.google.android.material.snackbar.Snackbar
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ListFragment : Fragment() {
    private var _binding: FragmentListBinding? = null
    private val binding get() = requireNotNull(_binding) {
        "View was destroyed"
    }

    private val adapter = CountryAdapter { item ->
        findNavController().navigate(
            ListFragmentDirections.toDetails(item.name)
        )
    }

    private var isLoading = false
    private var currentPage = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentListBinding.inflate(inflater, container, false)
            .also { binding ->
                _binding = binding
            }
            .root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadCountry()

        with(binding) {
            layoutSwiperefresh.setOnRefreshListener {
                adapter.submitList(emptyList())
                currentPage = 0
                loadCountry {
                    layoutSwiperefresh.isRefreshing = false
                }
            }

            val linearLayoutManager = LinearLayoutManager(
                view.context, LinearLayoutManager.VERTICAL, false
            )

            recyclerView.adapter = adapter
            recyclerView.layoutManager = linearLayoutManager
            recyclerView.addHorizontalSpaceDecoration(RECYCLER_ITEM_SPACE)
            recyclerView.addPaginationScrollListener(linearLayoutManager, COUNT_TO_LOAD) {
                loadCountry()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun loadCountry(onLoadingFinished: () -> Unit = {}) {
        if (isLoading) return

        isLoading = true

        val loadingFinishedCallback = {
            isLoading = false
            onLoadingFinished()
        }

        RetrofitService.CountryApi.getCountry()
            .enqueue(object : Callback<List<Country>> {
                override fun onResponse(
                    call: Call<List<Country>>,
                    response: Response<List<Country>>
                ) {
                    if (response.isSuccessful) {
                        val newList = adapter.currentList
                            .dropLastWhile { it == PagingData.Loading }
                            .plus(response.body()?.map { PagingData.Content(it) }.orEmpty())
                            .plus(PagingData.Loading)
                        adapter.submitList(newList)
                        currentPage++
                    } else {
                        handleErrors(response.errorBody()?.string() ?: GENERAL_ERROR_MESSAGE)
                    }

                    loadingFinishedCallback()
                }

                override fun onFailure(call: Call<List<Country>>, t: Throwable) {
                    handleErrors(t.message ?: GENERAL_ERROR_MESSAGE)
                    loadingFinishedCallback()
                }
            })
    }

    private fun handleErrors(errorMessage: String) {
        Snackbar.make(binding.root, errorMessage, Snackbar.LENGTH_SHORT)
            .setAction(android.R.string.ok) {}
            .show()
    }

    companion object {
        private const val RECYCLER_ITEM_SPACE = 50
        private const val COUNT_TO_LOAD = 15
        private const val GENERAL_ERROR_MESSAGE = "Something went wrong"
    }
}

fun RecyclerView.addHorizontalSpaceDecoration(space: Int) {
    addItemDecoration(
        object : RecyclerView.ItemDecoration() {
            override fun getItemOffsets(
                outRect: Rect,
                view: View,
                parent: RecyclerView,
                state: RecyclerView.State
            ) {
                val position = parent.getChildAdapterPosition(view)
                if (position != 0 && position != parent.adapter?.itemCount) {
                    outRect.top = space
                }
            }
        }
    )
}
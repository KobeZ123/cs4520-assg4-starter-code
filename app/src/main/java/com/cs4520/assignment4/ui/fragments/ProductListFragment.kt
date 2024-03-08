package com.cs4520.assignment4.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cs4520.assignment4.ui.adapters.ProductCardAdapter
import com.cs4520.assignment4.R
import com.cs4520.assignment4.domain.ProductListViewModel
import com.cs4520.assignment4.databinding.FragmentProductListBinding

/**
 * This ProductListFragment holds a RecyclerView to display a list of products.
 */
class ProductListFragment : Fragment() {

    private var _binding: FragmentProductListBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: ProductListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreate(savedInstanceState)
        _binding = FragmentProductListBinding.inflate(layoutInflater)

        // Inflate the layout for this fragment
        val rootView = inflater.inflate(R.layout.fragment_product_list, container, false)

//        val productList = productsDataset.mapToProductList()
        val productCardAdapter = ProductCardAdapter(emptyList())
        val recyclerView: RecyclerView = rootView.findViewById(R.id.product_list_view)
        recyclerView.adapter = productCardAdapter
        recyclerView.layoutManager = LinearLayoutManager(this.context)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)
        )[ProductListViewModel::class.java]

        initObservers()
    }

    /**
     * initializes live data observers
     */
    private fun initObservers() {
        viewModel.isLoading.observe(viewLifecycleOwner) {
            if (it) {
                binding.progressBar.visibility = View.VISIBLE
            } else {
                binding.progressBar.visibility = View.GONE
            }
        }

        viewModel.productListLiveData.observe(viewLifecycleOwner) {
            if (it.isEmpty()) {
                binding.noItemsText.visibility = View.VISIBLE
                binding.productListView.visibility = View.GONE
            } else {
                binding.productListView.visibility = View.VISIBLE
                binding.noItemsText.visibility = View.GONE
            }
        }

        viewModel.errorMessageLiveData.observe(viewLifecycleOwner) {
            if (it != null) {
                Toast.makeText(
                    this.context,
                    it,
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }
}
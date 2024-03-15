package com.cs4520.assignment4.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cs4520.assignment4.ui.adapters.ProductCardAdapter
import com.cs4520.assignment4.domain.ProductListViewModel
import com.cs4520.assignment4.databinding.FragmentProductListBinding

/**
 * This ProductListFragment holds a RecyclerView to display a list of products.
 */
class ProductListFragment : Fragment() {

    private var _binding: FragmentProductListBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: ProductListViewModel

    private lateinit var productCardAdapter: ProductCardAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        _binding = FragmentProductListBinding.inflate(layoutInflater)

        productCardAdapter = ProductCardAdapter(emptyList())
        val recyclerView: RecyclerView = binding.productListView
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

        binding.backButton.setOnClickListener{
            viewModel.onBackPageClick()
        }

        binding.nextButton.setOnClickListener{
            viewModel.onNextPageClick()
        }

        initObservers()
    }

    /**
     * initializes the observers in onResume
     */
    override fun onResume() {
        super.onResume()
        initObservers()
    }

    /**
     * initializes live data observers
     */
    private fun initObservers() {
        viewModel.isLoading.removeObservers(viewLifecycleOwner)
        viewModel.productListLiveData.removeObservers(viewLifecycleOwner)
        viewModel.errorMessageLiveData.removeObservers(viewLifecycleOwner)
        viewModel.pageNumberLiveData.removeObservers(viewLifecycleOwner)

        viewModel.isLoading.observe(viewLifecycleOwner) {
            if (it) {
                binding.progressBar.visibility = View.VISIBLE
            } else {
                binding.progressBar.visibility = View.GONE
            }
        }

        viewModel.productListLiveData.observe(viewLifecycleOwner) {
            if (it == null) {
                binding.noItemsText.visibility = View.GONE
                binding.productListView.visibility = View.GONE
            } else {
                productCardAdapter.updateData(it)

                if (it.isEmpty()) {
                    binding.noItemsText.visibility = View.VISIBLE
                    binding.productListView.visibility = View.GONE
                } else {
                    binding.productListView.visibility = View.VISIBLE
                    binding.noItemsText.visibility = View.GONE
                }
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

        viewModel.pageNumberLiveData.observe(viewLifecycleOwner) {
            viewModel.fetchProducts(it)
            Log.e("KOBE", "UPDATED PAGE NUMBER")
            binding.pageNumberText.text = it.toString()

            if (it <= 1) {
                binding.backButton.visibility = View.INVISIBLE
            } else {
                binding.backButton.visibility = View.VISIBLE
            }
        }
    }
}
package com.cs4520.assignment4.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.cs4520.assignment4.databinding.FragmentProductCardBinding

/**
 * This ProductCardFragment displays a single product.
 * It should be used for the RecyclerView.
 */
class ProductCardFragment : Fragment() {
    private var _binding: FragmentProductCardBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        _binding = FragmentProductCardBinding.inflate(layoutInflater)

        return binding.root
    }
}
package com.cs4520.assignment4.ui.adapters

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.cs4520.assignment4.R
import com.cs4520.assignment4.data.models.Product
import com.cs4520.assignment4.databinding.FragmentProductCardBinding

class ProductCardAdapter(
    private var dataSet: List<Product>
) : RecyclerView.Adapter<ProductCardAdapter.ViewHolder>() {
    /**
     * reference to view holder
     */
    class ViewHolder(binding: FragmentProductCardBinding) : RecyclerView.ViewHolder(binding.root) {
        val cardContainer: ConstraintLayout = binding.productCardContainer
        val imageView: ImageView = binding.productImage
        val nameText: TextView = binding.productNameText
        val priceText: TextView = binding.productPriceText
        val dateText: TextView = binding.productDateText
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = FragmentProductCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = dataSet.size

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val product: Product = dataSet[position]
        viewHolder.nameText.text = product.name
        viewHolder.priceText.text = String.format("$ %d", product.price)
        viewHolder.dateText.text = product.date
        viewHolder.dateText.visibility = if (product.date != null) View.VISIBLE else View.GONE

        when (product) {
            is Product.EquipmentProduct -> {
                viewHolder.cardContainer.setBackgroundColor(Color.parseColor("#E06666"))
                viewHolder.imageView.setImageResource(R.drawable.equipment)
            }
            is Product.FoodProduct -> {
                viewHolder.cardContainer.setBackgroundColor(Color.parseColor("#FFD965"))
                viewHolder.imageView.setImageResource(R.drawable.food)
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateData(newList: List<Product>) {
        dataSet = newList
        notifyDataSetChanged()
    }
}
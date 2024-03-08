package com.cs4520.assignment4.data.repository

import com.cs4520.assignment4.data.api.ProductClient
import com.cs4520.assignment4.data.local.entity.toProduct
import com.cs4520.assignment4.data.local.ProductDao
import com.cs4520.assignment4.data.api.models.Product

/**
 * implementation of the product repository to fetch product information
 */
class ProductRepositoryImpl(
    private val apiClient: ProductClient,
    private val productDao: ProductDao
) : ProductRepository {

    /**
     * attempts to make api request and returns the response body if successful
     * else gets product list from the local room database
     */
    override suspend fun getProduct(): List<Product>? {
        val response = apiClient.getProducts()
        return if (response.isSuccessful && response.body().isNullOrEmpty().not()) {
            response.body()
        } else {
            val networkProductList = productDao.getAllProducts()
            networkProductList.map{
                it.toProduct()
            }
        }
    }
}
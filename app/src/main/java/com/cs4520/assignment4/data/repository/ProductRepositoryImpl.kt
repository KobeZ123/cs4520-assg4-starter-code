package com.cs4520.assignment4.data.repository

import com.cs4520.assignment4.data.api.ProductClient
import com.cs4520.assignment4.data.models.toProduct
import com.cs4520.assignment4.data.local.ProductDao
import com.cs4520.assignment4.data.models.Product
import com.cs4520.assignment4.data.models.toProductDto

/**
 * implementation of the product repository to fetch product information
 */
class ProductRepositoryImpl(
    private val apiClient: ProductClient,
    private val productDao: ProductDao
) : ProductRepository {

    /**
     * attempts to make api request and returns the product list if request is successful
     * else gets product list from the local room database
     */
    override suspend fun getProduct(): List<Product>? {
        val response = apiClient.getProducts()
        return if (response.isSuccessful) {
            val productListFromApi = response.body()?.getData()
            productListFromApi?.let {
                productDao.addAllProducts(it.map{ product -> product.toProductDto() })
            }
            productListFromApi
        } else {
            val networkProductList = productDao.getAllProducts()
            networkProductList.map{
                it.toProduct()
            }
        }
    }
}
package com.cs4520.assignment4.data.api

import com.cs4520.assignment4.data.api.models.Product
import retrofit2.Response

class ProductClient(
    private val productApi: ProductApi
) {
    suspend fun getProducts(): Response<List<Product>> {
        return productApi.getProducts()
    }
}
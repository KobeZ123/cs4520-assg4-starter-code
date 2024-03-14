package com.cs4520.assignment4.data.api

import com.cs4520.assignment4.data.models.ProductApiResponse
import retrofit2.Response

class ProductClient(
    private val productApi: ProductApi
) {
    suspend fun getProducts(): Response<ProductApiResponse> {
        return productApi.getProducts()
    }
}
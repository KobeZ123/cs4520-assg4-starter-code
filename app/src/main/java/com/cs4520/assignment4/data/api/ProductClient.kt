package com.cs4520.assignment4.data.api

import com.cs4520.assignment4.data.models.ProductDto
import retrofit2.Response

class ProductClient(
    private val productApi: ProductApi
) {
    suspend fun getProducts(page: Int): Response<List<ProductDto>> {
        return productApi.getProducts(page)
    }
}
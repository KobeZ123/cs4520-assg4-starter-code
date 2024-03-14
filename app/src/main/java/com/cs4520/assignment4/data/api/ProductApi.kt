package com.cs4520.assignment4.data.api

import com.cs4520.assignment4.util.constants.Api
import com.cs4520.assignment4.data.models.ProductApiResponse
import retrofit2.Response
import retrofit2.http.GET

interface ProductApi {
    @GET(Api.ENDPOINT)
    suspend fun getProducts(): Response<ProductApiResponse>
}
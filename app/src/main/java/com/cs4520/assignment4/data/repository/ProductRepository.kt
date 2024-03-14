package com.cs4520.assignment4.data.repository

import com.cs4520.assignment4.data.models.Product

/**
 * This repository fetches product information.
 */
interface ProductRepository {
    suspend fun getProduct(): List<Product>?
}
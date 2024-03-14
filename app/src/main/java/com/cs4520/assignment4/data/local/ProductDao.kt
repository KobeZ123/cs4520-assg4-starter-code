package com.cs4520.assignment4.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.cs4520.assignment4.data.models.ProductDto

@Dao
interface ProductDao {
    @Query("SELECT * FROM product_table")
    fun getAllProducts(): List<ProductDto>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addAllProducts(products: List<ProductDto>)
}
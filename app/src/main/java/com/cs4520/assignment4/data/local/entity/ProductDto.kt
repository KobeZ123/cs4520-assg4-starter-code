package com.cs4520.assignment4.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.cs4520.assignment4.data.api.models.Product

/**
 * The ProductDTO in the Room database.
 */
@Entity(tableName = "product_table")
data class ProductDto (
    @ColumnInfo(name = "name") val name: String?,
    @ColumnInfo(name = "date") val date: String?,
    @ColumnInfo(name = "price") val price: Int?,
    @ColumnInfo(name = "type") val type: String?,
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
)

fun ProductDto.toProduct(): Product {
    return when (this.type) {
        "Equipment" ->
            Product.EquipmentProduct(this.name.orEmpty(), this.date, this.price ?: 0)
        "Food"->
            Product.FoodProduct(this.name.orEmpty(), this.date, this.price ?: 0)
        else ->
            Product.EquipmentProduct(this.name.orEmpty(), this.date, this.price ?: 0)
    }
}
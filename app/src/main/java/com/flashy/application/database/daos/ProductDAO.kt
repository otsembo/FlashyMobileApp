package com.flashy.application.database.daos

import androidx.lifecycle.LiveData
import androidx.room.*
import com.flashy.application.database.entities.Product

@Dao
interface ProductDAO {

    @Insert
    suspend fun addProduct(product: Product) : Long

    @Update
    suspend fun updateProduct(product: Product) : Int

    @Delete
    suspend fun deleteProduct(product: Product) : Int

    @Query("DELETE from products")
    suspend fun deleteAll() : Int

    @Query("SELECT * FROM products ORDER BY date DESC")
    suspend fun getLatestProducts() : List<Product>

    //find single product
    @Query("SELECT * FROM products WHERE id=:id")
    fun getProduct(id:Int) : Product


}
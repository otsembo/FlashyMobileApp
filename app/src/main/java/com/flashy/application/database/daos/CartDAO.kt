package com.flashy.application.database.daos

import androidx.room.*
import com.flashy.application.database.entities.Cart

@Dao
interface CartDAO {

    @Insert
    suspend fun insertCartItem(cart: Cart) : Long

    @Update
    suspend fun updateCartItem(cart: Cart): Int

    @Delete
    suspend fun deleteCartItem(cart: Cart) : Int

    @Query("DELETE FROM cart")
    suspend fun deleteAllCartItems() : Int

    @Query("SELECT * FROM cart")
    suspend fun getAllCartItems() : List<Cart>

}
package com.flashy.application.database.repositories

import com.flashy.application.database.daos.CartDAO
import com.flashy.application.database.entities.Cart
import com.flashy.application.database.entities.Category

class CartRepository(private val dao:CartDAO) {

    //delete all
    suspend fun deleteAll() : Int{
        return dao.deleteAllCartItems()
    }

    //add all information
    suspend fun addCartItem(cart: Cart){
        dao.insertCartItem(cart)
    }

    //get all cart items
    suspend fun getCartItems() : List<Cart>{
        return dao.getAllCartItems()
    }

}
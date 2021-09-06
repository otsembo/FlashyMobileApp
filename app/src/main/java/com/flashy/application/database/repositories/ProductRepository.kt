package com.flashy.application.database.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.flashy.application.database.daos.ProductDAO
import com.flashy.application.database.entities.Product

class ProductRepository(private val dao: ProductDAO) {

    //delete all
    suspend fun deleteAll() : Int{
        return dao.deleteAll()
    }

    //add all information
    suspend fun addProduct(product: Product){
        dao.addProduct(product)
    }

    //get all categories
    suspend fun retrieveProducts() : List<Product>{
        return dao.getLatestProducts()
    }

    //get single product
    fun findProduct(id: Int): LiveData<Product> {
        return MutableLiveData(dao.getProduct(id))
    }

}
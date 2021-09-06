package com.flashy.application.general

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.flashy.application.database.daos.ProductDAO
import com.flashy.application.database.entities.Product
import com.flashy.application.database.repositories.CategoryRepository
import com.flashy.application.database.repositories.ProductRepository

object FavoritesUtil {

    //favorite list
    val favList = ArrayList<Int>()

    fun productIdsAsArray(products : ArrayList<Product>) : IntArray{
        val productIds = IntArray(products.size)
        for(id in 0..products.lastIndex) productIds[id] = products[id].id
        return productIds
    }

    fun idsAsProducts(productDAO: ProductDAO): List<Product>{
        val products = ArrayList<Product>(favList.size)
        for (fav in favList){
            products.add(productDAO.getProduct(fav))
        }
        return products
    }

    fun saveToDurableState(outState: Bundle, ids:IntArray){
        outState.putIntArray("FAVORITES", ids)
    }

    fun reloadFromDurableState(savedInstanceState: Bundle?): IntArray? {
        return savedInstanceState?.getIntArray("FAVORITES")
    }

    private fun findAllFavorites(productRepository: ProductRepository, ids: IntArray) : List<Product>{
        val products = ArrayList<Product>()
        for (id in ids){
            products.add(productRepository.findProduct(id).value!!)
        }
        return products
    }

    fun saveToFavoritesList(productId: Int){
        if(!favList.contains(productId))
        favList.add(productId)
    }

    fun removeFromList(productId: Int){
        favList.remove(productId)
    }

    fun saveAllToArrayList(ids: IntArray){
        if(favList.isNotEmpty()) favList.clear()
        favList.addAll(ids.toTypedArray())
    }

    fun printAll(mCtx:Context){
        Toast.makeText(mCtx, "TEST: $favList", Toast.LENGTH_SHORT).show()
        Log.d("TAGGER", favList.toString())
    }

    fun isFavorite(id:Int):Boolean{
        return favList.contains(id)
    }

}
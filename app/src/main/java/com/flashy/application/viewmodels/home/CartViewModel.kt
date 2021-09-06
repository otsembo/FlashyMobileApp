package com.flashy.application.viewmodels.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.flashy.application.database.entities.Cart
import com.flashy.application.database.entities.Product
import com.flashy.application.database.repositories.CartRepository
import com.flashy.application.database.repositories.ProductRepository
import com.flashy.application.general.AppUtil
import com.flashy.application.network.ApiClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CartViewModel(private val repository: CartRepository, private val productRepository: ProductRepository) : ViewModel() {

    //live data object for cart items
    private val _cartItems  = MutableLiveData<List<Cart>>()
    val cartItems : LiveData<List<Cart>>
        get() = _cartItems

    //live data for product list
    private val _productList = MutableLiveData<List<Product>>()
    val productList:LiveData<List<Product>>
        get() = _productList

    private val userId = 1

    //function to handle network request
    suspend fun findCartItems(scope:CoroutineScope){
        //get response from server
        val cartResponse = ApiClient.apiService.getAllCartItems(userId)
        with(cartResponse){
            //check if response is successful
            if(isSuccessful){
                //retrieve information
                val cartBody = body()?.data as ArrayList<*>
                //convert to proper array
                val cartArray = AppUtil.convertTreeToElement(cartBody).asJsonArray
                //delete all existing cart items
                deleteAllCartItems()
                //loop through items to format the class
                for (cartObject in cartArray){
                    val singleCartObject = cartObject.asJsonObject
                    //cart entity
                    val cartEntity = Cart(
                        singleCartObject["id"].asInt,
                        singleCartObject["user_id"].asInt,
                        singleCartObject["product"].asJsonObject["id"].asInt,
                        singleCartObject["is_checked"].asBoolean,
                        singleCartObject["qty"].asInt,
                        singleCartObject["unit_price"].asDouble,
                        singleCartObject["is_deleted"].asBoolean,
                        singleCartObject["date_added"].asString
                    )
                    //add value to table
                    addToDatabase(cartEntity)
                }

                //items in database
                val cartItemsInDB = getAllItems()
                //update live data object
                scope.launch {
                    withContext(Dispatchers.Main){
                        _cartItems.value = cartItemsInDB
                        withContext(Dispatchers.IO){
                            val productItems = getProducts()
                            withContext(Dispatchers.Main){
                                _productList.value = productItems
                            }
                        }
                    }
                }

            }
        }

    }

    //find all favorite items
    private suspend fun getProducts() : List<Product>{
        val cartItems = _cartItems.value
        val tmpList = ArrayList<Product>()
        if(!cartItems.isNullOrEmpty()){
            for (cartItem in cartItems){
              val product = productRepository.findProduct(cartItem.product_id).value
                if (product != null) {
                    tmpList.add(product)
                }
            }
        }
        return tmpList
    }

    //remove all existing cart items
    private suspend fun deleteAllCartItems(){
        repository.deleteAll()
    }

    //add to database
    private suspend fun addToDatabase(cart: Cart){
        repository.addCartItem(cart)
    }

    //get all items from database
    private suspend fun getAllItems() : List<Cart>{
        return repository.getCartItems()
    }

}
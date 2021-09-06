package com.flashy.application.vmfactories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.flashy.application.database.repositories.CartRepository
import com.flashy.application.database.repositories.ProductRepository
import com.flashy.application.viewmodels.home.CartViewModel
import java.lang.IllegalArgumentException

class CartViewModelFactory(private val repository: CartRepository, private val productRepository: ProductRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(CartViewModel::class.java)){
            return CartViewModel(repository, productRepository) as T
        }
        throw IllegalArgumentException("Unknonw view model class")
    }

}
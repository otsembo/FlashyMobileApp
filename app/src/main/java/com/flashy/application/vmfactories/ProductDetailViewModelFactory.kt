package com.flashy.application.vmfactories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.flashy.application.database.repositories.ProductRepository
import com.flashy.application.viewmodels.home.HomeViewModel
import com.flashy.application.viewmodels.home.ProductDetailViewModel

class ProductDetailViewModelFactory(private val repository: ProductRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

        if(modelClass.isAssignableFrom(ProductDetailViewModel::class.java)){
            return ProductDetailViewModel(repository) as T
        }

        throw IllegalArgumentException("Unknown View Model Class")

    }
}
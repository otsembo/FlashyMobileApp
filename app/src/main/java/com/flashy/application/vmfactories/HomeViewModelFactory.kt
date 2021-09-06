package com.flashy.application.vmfactories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.flashy.application.database.repositories.CategoryRepository
import com.flashy.application.database.repositories.ProductRepository
import com.flashy.application.viewmodels.home.FavoriteViewModel
import com.flashy.application.viewmodels.home.HomeViewModel

class HomeViewModelFactory(private val repository: ProductRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

        if(modelClass.isAssignableFrom(HomeViewModel::class.java)){
            return HomeViewModel(repository) as T
        }

        throw IllegalArgumentException("Unknown View Model Class")

    }
}
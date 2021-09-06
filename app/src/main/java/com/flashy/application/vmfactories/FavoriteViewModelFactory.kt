package com.flashy.application.vmfactories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.flashy.application.database.repositories.CategoryRepository
import com.flashy.application.viewmodels.home.FavoriteViewModel

class FavoriteViewModelFactory(private val repository: CategoryRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

        if(modelClass.isAssignableFrom(FavoriteViewModel::class.java)){
            return FavoriteViewModel(repository) as T
        }

        throw IllegalArgumentException("Unknown View Model Class")

    }
}
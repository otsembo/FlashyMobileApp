package com.flashy.application.viewmodels.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.flashy.application.database.daos.ProductDAO
import com.flashy.application.database.entities.Category
import com.flashy.application.database.entities.Product
import com.flashy.application.database.repositories.CategoryRepository
import com.flashy.application.general.FavoritesUtil
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FavoriteViewModel(private val repository: CategoryRepository) : ViewModel() {

    //list of categories
    private val _favorites =  MutableLiveData<List<String>>()
    val favorites: LiveData<List<String>>
        get() = _favorites

    //favorites

    private val _favoriteProducts = ArrayList<Product>()
    val favoriteProducts : List<Product>
        get() = _favoriteProducts


    suspend fun getTitles(){
        CoroutineScope(Dispatchers.Main).launch {
            _favorites.value = repository.retrieveTitles()
        }
    }

    fun loadFavoriteProducts(productDAO: ProductDAO){
        _favoriteProducts.clear()
        _favoriteProducts.addAll(FavoritesUtil.idsAsProducts(productDAO))
    }


}
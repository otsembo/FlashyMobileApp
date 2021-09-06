package com.flashy.application.viewmodels.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.flashy.application.database.entities.Category
import com.flashy.application.database.entities.Images
import com.flashy.application.database.entities.Product
import com.flashy.application.database.repositories.CategoryRepository
import com.flashy.application.database.repositories.ImagesRepository
import com.flashy.application.database.repositories.ProductRepository
import com.flashy.application.general.AppPreference
import com.flashy.application.general.AppUtil
import com.flashy.application.general.FavoritesUtil
import com.flashy.application.network.ApiClient
import com.flashy.application.network.services.ApiService
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeViewModel(private val repository: ProductRepository) : ViewModel(){

    private val TAG = HomeViewModel::class.java.simpleName

    //product items
    private val _productList =  MutableLiveData<List<Product>>()
    val productList : LiveData<List<Product>>
        get() = _productList

    suspend fun findLatestProducts(scope:CoroutineScope){
        //retrieve latest products
        val response = ApiClient.apiService.getLatestProducts()
        //check if response is successful
        if(response.isSuccessful){
            val info = response.body()?.data as ArrayList<*>
            val productArray = AppUtil.convertTreeToElement(info).asJsonArray
            //remove all values
            removeAll()
            //loop through all products
            for(product in productArray){
                val productSubEntity  = AppUtil.gson.fromJson(product, com.flashy.application.database.entities.subentities.Product::class.java)
                val myProduct = Product(
                    productSubEntity.id,
                    productSubEntity.name,
                    productSubEntity.date,
                    productSubEntity.unit_price,
                    productSubEntity.qty_available,
                    productSubEntity.sizes,
                    productSubEntity.colors,
                    productSubEntity.banner,
                    productSubEntity.brands,
                    productSubEntity.category.id,
                    productSubEntity.description
                )
                //add product to database
                addProduct(myProduct)
            }
            //retrieve all products
            getProducts(scope)

        }

    }

    suspend fun findAllProductImages(scope: CoroutineScope, repo: ImagesRepository){
        val response = ApiClient.apiService.getAllImages()
        if(response.isSuccessful){
            val info = response.body()?.data as ArrayList<*>
            val imageArray = AppUtil.convertTreeToElement(info).asJsonArray
            removeImages(repo)
            for (image in imageArray){
                val images = AppUtil.gson.fromJson(image, Images::class.java)
                repo.addImage(images)
            }
        }
    }

    //retrieve latest products
    private suspend fun getProducts(scope: CoroutineScope){
        val products = repository.retrieveProducts()
        //set values on main thread
        scope.launch {
            withContext(Dispatchers.Main){
                _productList.value = products
            }
        }
    }

    //add product to database
    private suspend fun addProduct(product: Product){
        repository.addProduct(product)
    }

    private suspend fun removeAll(){
        repository.deleteAll()
    }

    private suspend fun removeImages(repo:ImagesRepository){
        repo.deleteAll()
    }

    fun reloadFavorites(appPreferences: AppPreference){
        FavoritesUtil.saveAllToArrayList(appPreferences.getIntArray("FAVS"))
    }

    fun saveFavorites(appPreferences: AppPreference){
       appPreferences.putIntArray("FAVS", FavoritesUtil.favList.toIntArray()).apply()
    }

}
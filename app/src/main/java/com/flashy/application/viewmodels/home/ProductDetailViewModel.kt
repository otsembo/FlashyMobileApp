package com.flashy.application.viewmodels.home

import android.content.Context
import android.widget.ArrayAdapter
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.flashy.application.R
import com.flashy.application.database.entities.Product
import com.flashy.application.database.repositories.ImagesRepository
import com.flashy.application.database.repositories.ProductRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ProductDetailViewModel(private val repository: ProductRepository) : ViewModel() {

    //sample images
    private val _images = MutableLiveData<Array<String>>()
    val images : LiveData<Array<String>>
        get() = _images

    //sample sizes
    private val _sizes = MutableLiveData<Array<String>>()
    val sizes : LiveData<Array<String>>
        get() = _sizes

    //sample colors
    private val _colors = MutableLiveData<Array<String>>()
    val colors : LiveData<Array<String>>
        get() = _colors

    //product item
    private val _product =  MutableLiveData<Product>()
    val product: LiveData<Product>
        get() = _product

    suspend fun getProductDetails(id:Int, scope:CoroutineScope){
        val prodValue = repository.findProduct(id)
        scope.launch {
            withContext(Dispatchers.Main){
                _product.value = prodValue.value
            }
        }

    }

    //size adapter
    fun getSizeAdapter(mCtx: Context, data:Array<String>): ArrayAdapter<String> {
        return ArrayAdapter(mCtx, R.layout.spinner_item, data)
    }

    //color adapter
    fun getColorAdapter(mCtx: Context, data:Array<String>) : ArrayAdapter<String>{
        return ArrayAdapter(mCtx, R.layout.spinner_item,data)
    }

    fun getSpinnerData(initValue:String, data: Array<String>) : Array<String>{
        val myArray = ArrayList<String>()
        myArray.add(initValue)
        myArray.addAll(data)
        return myArray.toTypedArray()
    }

    suspend fun getProductImages(repo:ImagesRepository, productId:Int, scope: CoroutineScope){
        //get all images
        val imagesList = repo.retrieveImages(productId)
        //append product banner at the top
        val allImagesList = ArrayList<String>()
        scope.launch {
            withContext(Dispatchers.Main){
                _product.value?.let { allImagesList.add(it.banner) }
                allImagesList.addAll(imagesList)
                _images.value = allImagesList.toTypedArray()
            }
        }

    }


}
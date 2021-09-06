package com.flashy.application.database.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.flashy.application.database.daos.ImagesDAO
import com.flashy.application.database.entities.Images
import com.flashy.application.database.entities.Product

class ImagesRepository(private val dao:ImagesDAO) {

    //delete all
    suspend fun deleteAll() : Int{
        return dao.deleteAll()
    }

    //add all information
    suspend fun addImage(image: Images){
        dao.addImage(image)
    }

    //get all categories
    suspend fun retrieveImages(product_id:Int) : List<String>{
        return dao.getProductImages(product_id)
    }


}
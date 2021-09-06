package com.flashy.application.database.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.flashy.application.database.daos.CategoryDAO
import com.flashy.application.database.entities.Category

class CategoryRepository(private val dao:CategoryDAO) {

    //delete all
    suspend fun deleteAll() : Int{
        return dao.deleteAll()
    }

    //add all information
    suspend fun addCategory(category: Category){
        dao.addCategory(category)
    }

    //get all categories
    suspend fun retrieveCategories() : List<Category>{
        return dao.getAllCategories()
    }

    //get category titles
    suspend fun retrieveTitles () : List<String>{
        return dao.getCategoryTitles()
    }

}
package com.flashy.application.viewmodels.home

import android.util.Log
import android.view.View
import androidx.lifecycle.*
import com.flashy.application.database.entities.Category
import com.flashy.application.database.repositories.CategoryRepository
import com.flashy.application.network.ApiClient
import com.flashy.application.network.services.ApiService
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import kotlinx.coroutines.*


class CategoryViewModel(private val repository: CategoryRepository) : ViewModel() {

    //list of categories
    private val _categories =  MutableLiveData<List<Category>>()
    val categories: LiveData<List<Category>>
        get() = _categories

    //category selected message
    private val _catMessage = MutableLiveData<String>()
    val catMessage : LiveData<String>
        get() = _catMessage

    //get network categories
    suspend fun loadCategories(scope: CoroutineScope) {
        //api call service
        //val apiCall : ApiService = ApiClient.apiClient().create(ApiService::class.java)
        //retrieve categories
        val response = ApiClient.apiService.getCategories()

        if (response.isSuccessful){
            //gson object
            val gson = Gson()
            //get data from response
            val info = response.body()?.data as ArrayList<*>
            val jsonArray = gson.toJsonTree(info).asJsonArray
            //delete all values present
            removeAll()
            //loop through all values in array
            for (jsonObject in jsonArray){
                val category:Category = gson.fromJson(jsonObject, Category::class.java)
                addCategory(category)
            }

            // find all categories in database
            retrieveCategories()

        }
    }

    //add categories to
    private suspend fun addCategory(category: Category){
        repository.addCategory(category)
    }

    private suspend fun removeAll(){
        repository.deleteAll()
    }

    //get all saved categories
    private suspend fun retrieveCategories() {
        CoroutineScope(Dispatchers.Main).launch {
            _categories.value = repository.retrieveCategories()
        }
    }

    fun showData(v: View){
        catMessage.value?.let { Snackbar.make(v, it, Snackbar.LENGTH_LONG).show() }
    }

    fun setCatvalue(message: String){
        _catMessage.value = message
    }

}
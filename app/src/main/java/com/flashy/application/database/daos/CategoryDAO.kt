package com.flashy.application.database.daos


import androidx.lifecycle.LiveData
import androidx.room.*
import com.flashy.application.database.entities.Category
import kotlinx.coroutines.flow.Flow


@Dao
interface CategoryDAO {

    @Insert
    suspend fun addCategory(category: Category) : Long

    @Update
    suspend fun updateCategory(category: Category) : Int

    @Delete
    suspend fun deleteCategory(category: Category) : Int

    @Query("DELETE from categories")
    suspend fun deleteAll() : Int

    @Query("SELECT * FROM categories")
    suspend fun getAllCategories() : List<Category>

    @Query("SELECT name FROM categories")
    suspend fun getCategoryTitles () : List<String>

}
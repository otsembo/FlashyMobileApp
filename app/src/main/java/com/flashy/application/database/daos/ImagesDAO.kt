package com.flashy.application.database.daos

import androidx.room.*
import com.flashy.application.database.entities.Images
import com.flashy.application.database.entities.Product

@Dao
interface ImagesDAO {

    @Insert
    suspend fun addImage(image: Images) : Long

    @Update
    suspend fun updateImage(image: Images) : Int

    @Delete
    suspend fun deleteImage(image: Images) : Int

    @Query("DELETE from images")
    suspend fun deleteAll() : Int

    @Query("SELECT url FROM images WHERE product_id = :product_id")
    suspend fun getProductImages(product_id:Int) : List<String>

}
package com.flashy.application.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.flashy.application.database.daos.CategoryDAO
import com.flashy.application.database.daos.ImagesDAO
import com.flashy.application.database.daos.ProductDAO
import com.flashy.application.database.entities.Category
import com.flashy.application.database.entities.Images
import com.flashy.application.database.entities.Product
import com.flashy.application.general.AppUtil

@Database(entities = [Category::class, Product::class, Images::class], version = 4)
abstract class AppDatabase : RoomDatabase() {
    //category dao
    abstract val categoryDAO :CategoryDAO
    //product dao
    abstract val productDAO  :ProductDAO
    //images dao
    abstract val imagesDAO   :ImagesDAO

    companion object{
        @Volatile
        private var INSTANCE: AppDatabase? = null
        //obtain instance
        fun getInstance(mCtx:Context) : AppDatabase{
            synchronized(this){
                var instance = INSTANCE
                if(instance == null){
                    instance = Room.databaseBuilder(mCtx.applicationContext, AppDatabase::class.java, AppUtil.DB_NAME)
                        .fallbackToDestructiveMigration()
                        .build()
                }
                return instance
            }
        }
    }

}
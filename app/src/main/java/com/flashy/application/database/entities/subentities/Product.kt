package com.flashy.application.database.entities.subentities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.flashy.application.database.entities.Category
import java.sql.Date

data class Product(

    val id:Int,

    val name:String,

    val date:String,

    val unit_price:Double,

    val qty_available:Int,

    val sizes:String,

    val colors:String,

    val banner:String,

    val brands:String,

    val category: Category,

    val description:String

)

package com.flashy.application.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.sql.Date

@Entity(tableName = "products")
data class Product(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id:Int,

    @ColumnInfo(name = "name")
    val name:String,

    @ColumnInfo(name = "date")
    val date:String,

    @ColumnInfo(name = "unit_price")
    val price:Double,

    @ColumnInfo(name = "qty_available")
    val qty:Int,

    @ColumnInfo(name = "sizes")
    val sizes:String,

    @ColumnInfo(name = "colors")
    val colors:String,

    @ColumnInfo(name = "banner")
    val banner:String,

    @ColumnInfo(name = "brands")
    val brands:String,

    @ColumnInfo(name = "category")
    val category: Int,

    @ColumnInfo(name = "description")
    val description:String

)

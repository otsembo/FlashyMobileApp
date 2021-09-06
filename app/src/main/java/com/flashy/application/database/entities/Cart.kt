package com.flashy.application.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cart")
data class Cart(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id:Int,

    @ColumnInfo(name = "user_id")
    var user_id:Int,

    @ColumnInfo(name = "product_id")
    var product_id:Int,

    @ColumnInfo(name = "is_checked")
    var is_checked:Boolean,

    @ColumnInfo(name = "qty")
    var qty:Int,

    @ColumnInfo(name = "unit_price")
    var unit_price:Double,

    @ColumnInfo(name = "is_deleted")
    var is_deleted:Boolean,

    @ColumnInfo(name = "date_added")
    var date_added:String

)

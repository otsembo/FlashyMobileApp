package com.flashy.application.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "images")
data class Images(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id:Int,

    @ColumnInfo(name = "product_id")
    var product_id: Int,

    @ColumnInfo(name = "type")
    var type:String,

    @ColumnInfo(name = "url")
    var url:String

)

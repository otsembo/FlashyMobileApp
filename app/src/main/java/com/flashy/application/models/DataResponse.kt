package com.flashy.application.models

import com.squareup.moshi.JsonClass

data class DataResponse(var message:String, var data:Any, var status:Int)
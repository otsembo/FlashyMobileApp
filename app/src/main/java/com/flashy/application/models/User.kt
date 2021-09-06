package com.flashy.application.models

import java.sql.Date

data class User(val id:Int?, val code:String?,val name:String, val email:String,
                val password:String, val date_registered:String?, val date_blocked:String?, val is_blocked:Boolean?,
                val address_id:Int?, val birth_date:String?)
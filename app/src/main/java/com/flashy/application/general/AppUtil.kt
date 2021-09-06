package com.flashy.application.general

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.flashy.application.activities.HomeActivity
import com.flashy.application.database.AppDatabase
import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.JsonElement
import java.math.BigInteger
import java.security.MessageDigest
import java.sql.Date
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

object AppUtil {

    const val preferenceName = "FlashyApp"

    //register form error messages
    const val emailLengthError = "That is too short to be an email address!"
    const val emailValidityError = "That does not seem to be a valid email!"
    const val passLengthError = "Your password needs to be at least 6 characters long!"
    const val nameLengthError = "Add a valid name!"

    //create gson object
    val gson = Gson()

    //database versions
    val dbVersion = 2

    // database name
    const val DB_NAME = "flashy"

    //hash values
    fun getHash(value:String): String{
        //get md5 hash
        val digest:MessageDigest = MessageDigest.getInstance("MD5")
        digest.update(value.toByteArray(), 0, value.length)
        return BigInteger(1, digest.digest()).toString(16)
    }

    //validate email
    fun isEmailValid(email:String) : Boolean{
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    fun checkLength(length:Int, value:String) : Pair<Boolean, String>{
        return if (value.length < length){
            Pair(false,"Enter a valid value")
        }else{
            Pair(true, "Success")
        }
    }

    fun generateUserCode() :String{
        return "FLASH-${UUID.randomUUID()}"
    }

    @SuppressLint("SimpleDateFormat")
    fun getTodayDate() : Date{
        return Date(System.currentTimeMillis())
    }

    //open the main page
    fun navigateToHome(mCtx:Context){
        //create intent
        val navigateIntent = Intent(mCtx, HomeActivity::class.java)
        mCtx.startActivity(navigateIntent)
    }

    //convert object to JSON TREE
    fun convertTreeToElement(info : ArrayList<*>) : JsonElement{
        //convert to json element
        return gson.toJsonTree(info)
    }

    //convert int to currency
    fun convertToCurrency(price: Number) : String{
        val formatter = DecimalFormat("#,###")
        return formatter.format(price)
    }

    fun initDB(mCtx:Context): AppDatabase {
        return Room.databaseBuilder(mCtx, AppDatabase::class.java, "flashy-database")
            .build()
    }


}
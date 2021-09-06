package com.flashy.application.general

import android.content.Context
import android.content.SharedPreferences

class AppPreference (var mCtx:Context) {

    //initialize preferences
    private val appPreferences:SharedPreferences by lazy {
        mCtx.getSharedPreferences(AppUtil.preferenceName, Context.MODE_PRIVATE)
    }

    //initialize preferences editor
    private val preferenceEditor:SharedPreferences.Editor by lazy {
        appPreferences.edit()
    }

    //save string
    fun putString(key:String, value:String) = preferenceEditor.putString(key, value).apply()

    //save boolean
    fun putBoolean(key: String, value: Boolean) = preferenceEditor.putBoolean(key, value).apply()

    //save int
    fun putInt(key: String, value: Int) = preferenceEditor.putInt(key, value).apply()

    //get string
    fun getString(key: String) = appPreferences.getString(key, "null")

    //get boolean
    fun getBoolean(key: String) = appPreferences.getBoolean(key, false)

    //get int
    fun getInt(key: String) = appPreferences.getInt(key, -99)

    //save int array
    fun putIntArray(key: String, ints:IntArray) = preferenceEditor.putIntArray(key, ints)

    //get int array
    fun getIntArray(key: String) = appPreferences.getIntArray(key)

    //extension function for IntArray storage
    fun SharedPreferences.Editor.putIntArray(key: String, value: IntArray) : SharedPreferences.Editor{
        return putString(key, value.joinToString(separator = ",", transform = {it.toString()}))
    }

    fun SharedPreferences.getIntArray(key: String) : IntArray{
        with(getString(key,"")){
            with(if (this?.isNotEmpty() == true) split(",") else return intArrayOf()){
                return IntArray(count()) { this[it].toInt() }
            }
        }
    }

}
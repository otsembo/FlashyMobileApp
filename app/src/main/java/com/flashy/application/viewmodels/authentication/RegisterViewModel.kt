package com.flashy.application.viewmodels.authentication

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.findNavController
import com.flashy.application.R
import com.flashy.application.general.AppUtil
import com.flashy.application.models.User
import com.flashy.application.network.ApiClient
import com.flashy.application.network.services.ApiService
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RegisterViewModel : ViewModel() {

    val tag = this::class.java.simpleName

    //progress bar visibility
    val isLoading:MutableLiveData<Boolean> = MutableLiveData(false)

    fun openLoginScreen(view: View){
        //navigate using action
        view.findNavController().navigate(R.id.action_register_to_login)
    }


    suspend fun registerUser(registrationInfo:Array<String>, v:View, scope: CoroutineScope) : Boolean{
        //check if registration is successful
        var isSuccess = false
        //api call service
        val apiCall : ApiService = ApiClient.apiService
        //user information
        val userInfo = User(null,AppUtil.generateUserCode(),registrationInfo[0],registrationInfo[1],
            registrationInfo[2],AppUtil.getTodayDate().toString(),null,false,
            null,null)
        //create post
        val response = apiCall.registerUser(userInfo)

        scope.launch {
            withContext(Dispatchers.Main){
                //check if the user is null
                if (response.isSuccessful){
                    Snackbar.make(v, response.body()?.message!!, Snackbar.LENGTH_LONG).show()
                    isSuccess = true
                }else{
                    Snackbar.make(v, response.errorBody().toString(), Snackbar.LENGTH_LONG).show()
                }
            }
        }
        return isSuccess
    }

    //validate registration
    fun validateCredentials(edtMail:String, edtPass:String, edtName:String,
                                    tilEmail:TextInputLayout, tilName:TextInputLayout, tilPassword:TextInputLayout) : Pair<Boolean, Array<String>>{
        var isValid = true

        val dataArray = arrayOf<String>(edtName, edtMail, AppUtil.getHash(edtPass))
        //check length of email section
        if(!AppUtil.checkLength(6, edtMail).first) {
            tilEmail.error = AppUtil.emailLengthError
            isValid = false
        }else{
            tilEmail.error = null
        }
        //check validity of email
        if(!AppUtil.isEmailValid(edtMail)){
            tilEmail.error = AppUtil.emailValidityError
            isValid = false
        }else{
            tilEmail.error = null
        }
        //check password length
        if(!AppUtil.checkLength(6, edtPass).first){
            tilPassword.error = AppUtil.passLengthError
            isValid = false
        }else{
            tilPassword.error = null
        }
        //check name length
        if(!AppUtil.checkLength(4, edtName).first){
            tilName.error = AppUtil.nameLengthError
            isValid = false
        }else{
            tilName.error = null
        }
        return Pair(isValid, dataArray)
    }


}
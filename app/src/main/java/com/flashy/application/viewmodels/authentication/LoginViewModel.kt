package com.flashy.application.viewmodels.authentication

import android.view.View
import androidx.lifecycle.ViewModel
import androidx.navigation.findNavController
import com.flashy.application.R

class LoginViewModel : ViewModel() {

    fun openResetPassword(view: View){
        //navigate using action
        view.findNavController().navigate(R.id.action_login_to_resetPassword)
    }

}
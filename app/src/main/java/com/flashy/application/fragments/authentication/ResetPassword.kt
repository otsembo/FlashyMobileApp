package com.flashy.application.fragments.authentication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.flashy.application.R
import com.flashy.application.databinding.FragmentResetPassBinding
import com.flashy.application.viewmodels.authentication.ResetPasswordViewModel

class ResetPassword : Fragment() {


    //instantiate binding
    private lateinit var binding:FragmentResetPassBinding

    //initialize viewmodel
    private val resetPasswordViewModel by lazy{
        ViewModelProvider(this)[ResetPasswordViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        //initialize binding
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_reset_pass, container, false)

        //initialize binding view model
        binding.apply {
            resetPassVM = resetPasswordViewModel
        }

        return binding.root
    }


}
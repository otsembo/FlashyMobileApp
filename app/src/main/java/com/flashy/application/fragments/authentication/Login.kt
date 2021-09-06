package com.flashy.application.fragments.authentication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.flashy.application.R
import com.flashy.application.databinding.FragmentLoginBinding
import com.flashy.application.viewmodels.authentication.LoginViewModel

class Login : Fragment() {

    private lateinit var binding: FragmentLoginBinding

    //initialize view model
    private val viewModel by lazy{
        ViewModelProvider(this).get(LoginViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //initialize binding object
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_login , container,false)

        //initialize view model
        binding.loginVM = viewModel

        //return view
        return binding.root
    }

}
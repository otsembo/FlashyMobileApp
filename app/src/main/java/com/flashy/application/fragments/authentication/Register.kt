package com.flashy.application.fragments.authentication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.flashy.application.R
import com.flashy.application.databinding.FragmentRegisterBinding
import com.flashy.application.general.AppUtil
import com.flashy.application.viewmodels.authentication.RegisterViewModel
import kotlinx.coroutines.*

class Register : Fragment() {

    //data binding object
    private lateinit var binding:FragmentRegisterBinding

    //view-model
    private val viewModel by lazy{
        ViewModelProvider(this).get(RegisterViewModel::class.java)
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        //initialize binding
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_register, container, false)
        //bind view-model
        binding.registerVM = viewModel
        //generate root view
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //add click listeners to the button
        binding.apply {
            btnRegister.setOnClickListener {
                //create account
                createAccount(this, it)
            }
        }
    }
    //create account function
    private fun createAccount(binding: FragmentRegisterBinding, it:View){
        //check if valid data
        binding.apply {
            val registrationPair:Pair<Boolean, Array<String>> = viewModel.validateCredentials(
                edtRegisterEmail.text.toString(),
                edtRegisterPassword.text.toString(),
                edtRegisterName.text.toString(),
                tilEmail,
                tilPassword,
                tilName
            )

            val validData = registrationPair.first
            val registrationInfo = registrationPair.second
            if(validData) {
                //visibility on
                viewModel.isLoading.value = true
                CoroutineScope(Dispatchers.IO).launch {
                    val registered = viewModel.registerUser(registrationInfo, it, this)
                    withContext(Dispatchers.Main){
                        //visibility off
                        viewModel.isLoading.value = false
                        //log in successful
                        if(registered) AppUtil.navigateToHome(this@Register.requireContext())
                    }
                }
            }
        }


    }






}
package com.flashy.application.fragments.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.flashy.application.R
import com.flashy.application.databinding.FragmentHomeCartBinding
import com.flashy.application.viewmodels.home.CartViewModel

class Cart : Fragment() {

    //binding object
    private lateinit var binding:FragmentHomeCartBinding

    //view model object
    private val viewModel by lazy{
        ViewModelProvider(this).get(CartViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        //initialize binding
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home_cart, container, false)
        //initialize view model
        binding.cartVM = viewModel
        //return view
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }


}
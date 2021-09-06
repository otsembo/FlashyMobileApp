package com.flashy.application.fragments.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.flashy.application.R
import com.flashy.application.adapters.CartAdapter
import com.flashy.application.database.AppDatabase
import com.flashy.application.database.daos.CartDAO
import com.flashy.application.database.repositories.CartRepository
import com.flashy.application.database.repositories.ProductRepository
import com.flashy.application.databinding.FragmentHomeCartBinding
import com.flashy.application.viewmodels.home.CartViewModel
import com.flashy.application.vmfactories.CartViewModelFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class Cart : Fragment() {

    //binding object
    private lateinit var binding:FragmentHomeCartBinding

    //view model object
    private lateinit var viewModel: CartViewModel

    //dao
    private lateinit var dao: CartDAO

    //repository
    private lateinit var repository: CartRepository

    //product repository
    private lateinit var productRepo:ProductRepository

    //adapter
    private lateinit var adapter:CartAdapter


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        //initialize binding
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home_cart, container, false)
        //init vars
        CoroutineScope(Dispatchers.IO).launch {
            initVars()
            this.launch(Dispatchers.Main){
                //initialize view model
                binding.cartVM = viewModel
            }
        }
        //return view
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //initialize adapter
        adapter = CartAdapter()
        //set recyclerview adapter
        binding.rvCart.adapter = adapter

        CoroutineScope(Dispatchers.IO).launch {
            viewModel.findCartItems(this)
        }

        initObservers()
    }

    //initialize necessary variables
    private fun initVars(){
        dao = AppDatabase.getInstance(requireActivity().application).cartDAO
        val prodDao = AppDatabase.getInstance(requireActivity().application).productDAO
        repository = CartRepository(dao)
        productRepo = ProductRepository(prodDao)
        viewModel = CartViewModelFactory(repository, productRepo).create(CartViewModel::class.java)
    }

    //initialize observers
    private fun initObservers(){
        viewModel.productList.observe(viewLifecycleOwner, {
            viewModel.cartItems.value?.let { it1 -> adapter.setItems(it1, it) }
            adapter.notifyDataSetChanged()
        })
    }

}
package com.flashy.application.fragments.home

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.flashy.application.R
import com.flashy.application.adapters.CategoryAdapter
import com.flashy.application.database.AppDatabase
import com.flashy.application.database.daos.CategoryDAO
import com.flashy.application.database.repositories.CategoryRepository
import com.flashy.application.databinding.FragmentHomeSearchBinding
import com.flashy.application.general.AppUtil
import com.flashy.application.viewmodels.home.CategoryViewModel
import com.flashy.application.vmfactories.CategoryViewModelFactory
import com.google.android.material.tabs.TabLayout
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class Search : Fragment() {

    private lateinit var binding: FragmentHomeSearchBinding

    private lateinit var dao:CategoryDAO

    private lateinit var repo:CategoryRepository

    private lateinit var viewModel:CategoryViewModel

    private lateinit var factory: CategoryViewModelFactory
    //recycler adapter
    private lateinit var categoryAdapter: CategoryAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //initialize binding
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home_search, container, false)
        binding.lifecycleOwner = this
        //on click listeners
        tabClicks()
        return binding.root
    }

    //click listeners to tabitems
    private fun tabClicks(){
        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when(tab?.position){
                    0 -> viewModel.setCatvalue("WOMEN'S CATEGORY")
                    1 -> viewModel.setCatvalue("MEN'S CATEGORY")
                    2 -> viewModel.setCatvalue("KIDS' CATEGORY")
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

        })
    }

    private fun initVariables(){
        dao = AppDatabase.getInstance(requireActivity().application).categoryDAO
        repo = CategoryRepository(dao)
        factory = CategoryViewModelFactory(repo)
        viewModel = ViewModelProvider(requireActivity(),factory).get(CategoryViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        CoroutineScope(Dispatchers.IO).launch{
            //initialize variables
            initVariables()
            //display data
            CoroutineScope(Dispatchers.Main).launch {
                loadData(view)
                initRecyclerView()
            }

        }
    }

    private fun loadData(v:View){
        CoroutineScope(Dispatchers.IO).launch {
            viewModel.loadCategories(this)
        }
    }



    //initialize recyclerview
    private fun initRecyclerView(){
        categoryAdapter = CategoryAdapter()
        binding.rvCategories.adapter = categoryAdapter
        displayCategories()
    }


    //display category
    private fun displayCategories(){
        viewModel.categories.observe(viewLifecycleOwner, {
            if(it != null) categoryAdapter.setItems(it)
            categoryAdapter.notifyDataSetChanged()
        })

        viewModel.catMessage.observe(viewLifecycleOwner, {
            viewModel.showData(binding.root)
        })

    }

}
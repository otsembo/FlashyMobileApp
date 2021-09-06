package com.flashy.application.fragments.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.flashy.application.R
import com.flashy.application.adapters.ProductAdapter
import com.flashy.application.database.AppDatabase
import com.flashy.application.database.daos.ImagesDAO
import com.flashy.application.database.daos.ProductDAO
import com.flashy.application.database.repositories.ImagesRepository
import com.flashy.application.database.repositories.ProductRepository
import com.flashy.application.databinding.FragmentHomeHomeBinding
import com.flashy.application.general.AppPreference
import com.flashy.application.general.FavoritesUtil
import com.flashy.application.viewmodels.home.HomeViewModel
import com.flashy.application.vmfactories.HomeViewModelFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class Home : Fragment() {

    private lateinit var binding: FragmentHomeHomeBinding

    private lateinit var dao:ProductDAO

    private lateinit var imagesDao: ImagesDAO

    private lateinit var repo: ProductRepository

    private lateinit var imagesRepository: ImagesRepository

    private lateinit var viewModel: HomeViewModel

    private lateinit var factory: HomeViewModelFactory

    private lateinit var adapter: ProductAdapter

    private val appPreferences:AppPreference by lazy {
        AppPreference(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //initialize binding
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home_home, container, false)
        return binding.root
    }



    private fun initVariables(){
        dao = AppDatabase.getInstance(requireActivity().application).productDAO
        imagesDao = AppDatabase.getInstance(requireActivity().application).imagesDAO
        repo = ProductRepository(dao)
        imagesRepository = ImagesRepository(imagesDao)
        factory = HomeViewModelFactory(repo)
        viewModel = ViewModelProvider(requireActivity(),factory).get(HomeViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //init variable in the background
        CoroutineScope(Dispatchers.IO).launch{
            //initialize variables
            initVariables()
            //load data
            loadData()
            //use main thread to init data
            this.launch {
                withContext(Dispatchers.Main){
                    initRecyclerView()
                    //reload all favorites
                    viewModel.reloadFavorites(appPreferences)
                }
            }

        }

    }

    private fun loadData(){
        CoroutineScope(Dispatchers.IO).launch {
            viewModel.findLatestProducts(this)
            viewModel.findAllProductImages(this, imagesRepository)
        }
    }

    //initialize recyclerview
    private fun initRecyclerView(){
        adapter = ProductAdapter()
        binding.rvArrivals.adapter = adapter
        displayCategories()
    }

    //display category
    private fun displayCategories(){
        viewModel.productList.observe(viewLifecycleOwner, {
            if(it != null) adapter.setItems(it)
            adapter.notifyDataSetChanged()
        })
    }


    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        viewModel.saveFavorites(appPreferences)
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.saveFavorites(appPreferences)
    }

    override fun onResume() {
        super.onResume()
        viewModel.reloadFavorites(appPreferences)
    }



}
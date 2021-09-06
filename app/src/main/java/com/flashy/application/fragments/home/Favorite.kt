package com.flashy.application.fragments.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.flashy.application.R
import com.flashy.application.adapters.CategoryAdapter
import com.flashy.application.adapters.FavCategoryAdapter
import com.flashy.application.adapters.ProductAdapter
import com.flashy.application.database.AppDatabase
import com.flashy.application.database.daos.CategoryDAO
import com.flashy.application.database.daos.ProductDAO
import com.flashy.application.database.repositories.CategoryRepository
import com.flashy.application.databinding.FragmentHomeFavoriteBinding
import com.flashy.application.databinding.FragmentHomeSearchBinding
import com.flashy.application.general.AppUtil
import com.flashy.application.viewmodels.home.CategoryViewModel
import com.flashy.application.viewmodels.home.FavoriteViewModel
import com.flashy.application.vmfactories.CategoryViewModelFactory
import com.flashy.application.vmfactories.FavoriteViewModelFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class Favorite : Fragment() {

    //initialize binding
    private lateinit var binding: FragmentHomeFavoriteBinding

    private lateinit var dao: CategoryDAO

    private lateinit var productDAO: ProductDAO

    private lateinit var repo: CategoryRepository

    private lateinit var viewModel: FavoriteViewModel

    private lateinit var factory: FavoriteViewModelFactory
    //recycler adapter
    private lateinit var categoryAdapter: FavCategoryAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        //initialize binding
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home_favorite, container, false)
        binding.lifecycleOwner = this

        //reference database
        CoroutineScope(Dispatchers.IO).launch {
          AppUtil.initDB(requireContext())
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        CoroutineScope(Dispatchers.IO).launch{
            //initialize variables
            initVariables()

            CoroutineScope(Dispatchers.Main).launch {
                loadData(view)
                initRecyclerView()
            }

        }

    }

    private fun initVariables(){
        dao = AppDatabase.getInstance(requireActivity().application).categoryDAO
        productDAO = AppDatabase.getInstance(requireActivity().application).productDAO
        repo = CategoryRepository(dao)
        factory = FavoriteViewModelFactory(repo)
        viewModel = ViewModelProvider(requireActivity(),factory).get(FavoriteViewModel::class.java)
    }

    private fun loadData(v:View){
        CoroutineScope(Dispatchers.IO).launch {
            viewModel.getTitles()
            viewModel.loadFavoriteProducts(productDAO)
        }
    }

    //initialize recyclerview
    private fun initRecyclerView(){
        categoryAdapter = FavCategoryAdapter()
        binding.rvChipsCategories.adapter = categoryAdapter
        displayCategories()
        val favAdapter = ProductAdapter()
        favAdapter.setItems(viewModel.favoriteProducts)
        binding.rvFavs.adapter = favAdapter
    }


    //display category
    private fun displayCategories(){
        viewModel.favorites.observe(viewLifecycleOwner, {
            if(it != null) categoryAdapter.setTitles(it)
            categoryAdapter.notifyDataSetChanged()
        })

    }

}
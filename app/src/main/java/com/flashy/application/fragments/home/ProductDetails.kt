package com.flashy.application.fragments.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.transition.TransitionInflater
import com.flashy.application.R
import com.flashy.application.adapters.pagers.ImageSliderAdapter
import com.flashy.application.database.AppDatabase
import com.flashy.application.database.daos.ImagesDAO
import com.flashy.application.database.daos.ProductDAO
import com.flashy.application.database.repositories.ImagesRepository
import com.flashy.application.database.repositories.ProductRepository
import com.flashy.application.databinding.FragmentProductDetailBinding
import com.flashy.application.general.AppUtil
import com.flashy.application.viewmodels.home.HomeViewModel
import com.flashy.application.viewmodels.home.ProductDetailViewModel
import com.flashy.application.vmfactories.HomeViewModelFactory
import com.flashy.application.vmfactories.ProductDetailViewModelFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ProductDetails : Fragment() {

    private lateinit var binding:FragmentProductDetailBinding
    private  val productItem : Bundle? by lazy{
        arguments
    }

    private lateinit var dao: ProductDAO

    private lateinit var repo: ProductRepository

    private lateinit var factory: ProductDetailViewModelFactory
    //pager adapter
    private lateinit var imageSliderAdapter: ImageSliderAdapter

    private lateinit var viewModel: ProductDetailViewModel

    //images info
    private lateinit var imagesDao: ImagesDAO
    private lateinit var imagesRepo: ImagesRepository

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_product_detail, container, false)
        //lifecycle owner
        binding.lifecycleOwner = this
        //retrieve arguments
        productItem?.let { ProductDetailsArgs.fromBundle(it).productId.toString() }?.let {
            Log.d("TAGGED",
                it
            )
        }
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        CoroutineScope(Dispatchers.IO).launch {
            //database components
            initVariables()
            //find view model details
            viewModel.getProductDetails(getProductId(), this)
            //find images
            viewModel.getProductImages(imagesRepo,getProductId(),this)
            //do ui stuff
            withContext(Dispatchers.Main){
                //init all view items
                initViewFunctions()
            }

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //inflater
        val inflater = TransitionInflater.from(requireContext())
        sharedElementEnterTransition = inflater.inflateTransition(android.R.transition.slide_top)
    }

    @SuppressLint("SetTextI18n")
    private fun initViewFunctions(){
        //initialize adapter
        imageSliderAdapter = ImageSliderAdapter(requireContext(), arrayOf(""))

        binding.apply {
            //make text view scrollable
            txtProductDescription.movementMethod = ScrollingMovementMethod()
            //set adapter to view pager
            viewPager2.adapter = imageSliderAdapter
        }

        viewModel.product.observe(viewLifecycleOwner,{
            if(it!= null){
                binding.apply {
                    txtProductName.text = it.name
                    txtProductDescription.text = it.description
                    txtPrice.text = "Ksh ${AppUtil.convertToCurrency(it.price)}"
                    //set spinner values
                    spinnerSize.adapter = viewModel.getSizeAdapter(requireContext(), viewModel.getSpinnerData("SIZES", it.sizes.split(",").toTypedArray()))
                    spinnerColor.adapter = viewModel.getColorAdapter(requireContext(), viewModel.getSpinnerData("COLORS", it.colors.split(",").toTypedArray()))
                }
            }
        })

        viewModel.images.observe(viewLifecycleOwner,{
            if(it!=null){
                imageSliderAdapter.setItems(it)
                imageSliderAdapter.notifyDataSetChanged()
            }
        })

    }
    //id value
    private fun getProductId() : Int = ProductDetailsArgs.fromBundle(requireArguments()).productId

    private fun initVariables(){
        dao = AppDatabase.getInstance(requireActivity().application).productDAO
        imagesDao = AppDatabase.getInstance(requireActivity().application).imagesDAO
        repo = ProductRepository(dao)
        imagesRepo = ImagesRepository(imagesDao)
        factory = ProductDetailViewModelFactory(repo)
        viewModel = ViewModelProvider(requireActivity(),factory).get(ProductDetailViewModel::class.java)
    }

}
package com.flashy.application.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.flashy.application.R
import com.flashy.application.database.entities.Product
import com.flashy.application.databinding.RowProductItemBinding
import com.flashy.application.viewholders.ProductVH

class ProductAdapter : RecyclerView.Adapter<ProductVH>(){

    //products
    private val products: ArrayList<Product> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductVH {
        //initialize inflater
        val inflater = LayoutInflater.from(parent.context)
        // binding object
        val binding = DataBindingUtil.inflate<RowProductItemBinding>(inflater, R.layout.row_product_item, parent, false)
        //return actual value
        return ProductVH(parent.context, binding)
    }

    override fun onBindViewHolder(holder: ProductVH, position: Int) {
        //bind items
        holder.bind(products[position])
    }

    override fun getItemCount(): Int = products.size

    //set items
    fun setItems(productList: List<Product>){
        products.clear()
        products.addAll(productList)
    }


}
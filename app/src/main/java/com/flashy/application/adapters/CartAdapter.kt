package com.flashy.application.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.flashy.application.R
import com.flashy.application.database.entities.Cart
import com.flashy.application.database.entities.Product
import com.flashy.application.databinding.RowCartBinding
import com.flashy.application.general.AppUtil

class CartAdapter : RecyclerView.Adapter<CartAdapter.CartVH>(){

    private val cartList = ArrayList<Cart>()
    private val productList = ArrayList<Product>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartVH {
        val binding :RowCartBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.row_cart, parent, false)
        return CartVH(parent.context, binding)
    }

    override fun onBindViewHolder(holder: CartVH, position: Int) {
        holder.bind(cartList[position], productList[position])
    }

    override fun getItemCount(): Int = cartList.size

    //set items
    fun setItems(_cartList:List<Cart>, _productList:List<Product>){
        cartList.clear()
        productList.clear()
        cartList.addAll(_cartList)
        productList.addAll(_productList)
    }

    inner class CartVH(private val mCtx:Context, private val binding:RowCartBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(cart: Cart, product: Product){
            with(binding){
                Glide.with(mCtx).load(product.banner).into(imgCartImage)
                txtBrand.text = product.brands
                txtTitle.text = product.name
                txtColor.text = product.colors.split(",")[0]
                txtCost.text = "Ksh ${ AppUtil.convertToCurrency(product.price) }"
                txtQuantity.text = cart.qty.toString()
                txtSize.text = product.sizes.split(",")[0]
            }
        }
    }

}
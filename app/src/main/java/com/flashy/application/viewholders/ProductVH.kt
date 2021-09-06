package com.flashy.application.viewholders

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.view.View
import androidx.core.view.ViewCompat
import androidx.navigation.findNavController
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.flashy.application.R
import com.flashy.application.database.entities.Product
import com.flashy.application.databinding.RowProductItemBinding
import com.flashy.application.fragments.home.HomeDirections
import com.flashy.application.general.AppUtil
import com.flashy.application.general.FavoritesUtil
import com.google.android.material.snackbar.Snackbar

class ProductVH(val mCtx:Context, val binding:RowProductItemBinding) : RecyclerView.ViewHolder(binding.root) {
    @SuppressLint("SetTextI18n")
    fun bind(product: Product){
        binding.txtCostFav.text = "Ksh ${AppUtil.convertToCurrency(product.price)}"
        binding.txtFavBrand.text = product.brands
        binding.txtFavTitleProduct.text = product.name

        if(FavoritesUtil.isFavorite(product.id)){
            binding.imageView2.setImageResource(R.drawable.ic_baseline_favorite_24)
        }

        binding.imageView2.setOnClickListener {

            if(FavoritesUtil.isFavorite(product.id)){
                FavoritesUtil.removeFromList(product.id)
                binding.imageView2.setImageResource(R.drawable.ic_baseline_favorite_border_24)

                Snackbar.make(binding.root, "Removed from favorites", Snackbar.LENGTH_LONG)
                    .setAction("UNDO"){
                        FavoritesUtil.saveToFavoritesList(productId = product.id)
                        binding.imageView2.setImageResource(R.drawable.ic_baseline_favorite_24)
                    }.
                    setActionTextColor(Color.RED).
                    show()

            }else{
                FavoritesUtil.saveToFavoritesList(product.id)
                binding.imageView2.setImageResource(R.drawable.ic_baseline_favorite_24)

                Snackbar.make(binding.root, "Added to favorites", Snackbar.LENGTH_LONG)
                    .setAction("UNDO"){
                        FavoritesUtil.removeFromList(productId = product.id)
                        binding.imageView2.setImageResource(R.drawable.ic_baseline_favorite_border_24)
                    }.
                    setActionTextColor(Color.RED).
                    show()

            }
        }
        Glide.with(mCtx).load(product.banner).placeholder(R.drawable.fancy_loader).into(binding.favImage)
        ViewCompat.setTransitionName(binding.favImage, "item_image")
        binding.favImage.setOnClickListener {
            navigateToDetails(product.id)
        }
    }

    private fun navigateToDetails(id: Int){
        val action = HomeDirections.actionHomeFragmentToProductDetails().setProductId(id)
        val extras = FragmentNavigatorExtras(binding.favImage to "hero_image")
        binding.root.findNavController().navigate(action, extras)
    }

}
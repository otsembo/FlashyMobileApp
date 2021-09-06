package com.flashy.application.adapters.pagers

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.view.ViewCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.PagerAdapter
import com.bumptech.glide.Glide
import com.flashy.application.R
import com.flashy.application.database.entities.Images
import com.flashy.application.databinding.PagerProductImagesBinding

     class ImageSliderAdapter(private val mCtx: Context, private var images: Array<String>) : RecyclerView.Adapter<ImageSliderAdapter.ImageViewHolder>(){

        inner class ImageViewHolder(val binding: PagerProductImagesBinding) : RecyclerView.ViewHolder(binding.root){
            fun bind(url:String){
                Glide.with(mCtx).load(url).into(binding.imgProductPager)
                ViewCompat.setTransitionName(binding.imgProductPager, "hero_image")
            }
        }
         override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
             val inflater = LayoutInflater.from(parent.context)
             val itemBinding:PagerProductImagesBinding = DataBindingUtil.inflate(inflater, R.layout.pager_product_images, parent, false)
             return ImageViewHolder(itemBinding)
         }

         override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
            holder.bind(images[position])
         }

         override fun getItemCount(): Int = images.size

         fun setItems(imagesList:Array<String>){
             images = imagesList
         }

     }

package com.flashy.application.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.flashy.application.R
import com.flashy.application.database.entities.Category
import com.flashy.application.databinding.RowSearchCategoriesBinding

class CategoryAdapter() : RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {

    //list of categories
    private val categoryList = ArrayList<Category>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {

        //get the layout inflater
        val inflater = LayoutInflater.from(parent.context)
        //item binding
        val binding:RowSearchCategoriesBinding = DataBindingUtil.inflate(inflater, R.layout.row_search_categories, parent, false)
        //return the view holder
        return CategoryViewHolder(binding, parent.context)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.bind(categoryList[position])
    }

    override fun getItemCount(): Int = categoryList.size

    //function to set list values
    fun setItems(categories: List<Category>){
        categoryList.clear()
        categoryList.addAll(categories)
    }


    class CategoryViewHolder(val binding: RowSearchCategoriesBinding, private val mCtx:Context) : RecyclerView.ViewHolder(binding.root){

        fun bind(category: Category){
            binding.txtCategoryTitle.text = category.name
            Glide.with(mCtx).load(category.image).into(binding.imgCatBanner)
        }

    }

}
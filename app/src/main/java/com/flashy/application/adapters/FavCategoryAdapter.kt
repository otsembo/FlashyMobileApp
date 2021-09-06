package com.flashy.application.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.view.menu.MenuView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.flashy.application.R
import com.flashy.application.databinding.RowFavChipsBinding

class FavCategoryAdapter : RecyclerView.Adapter<FavCategoryAdapter.FavViewHolder>() {

    private val titles = ArrayList<String>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<RowFavChipsBinding>(inflater, R.layout.row_fav_chips, parent, false)
        return FavViewHolder(binding, parent.context)
    }

    override fun onBindViewHolder(holder: FavViewHolder, position: Int) {
        holder.bind(titles[position])
    }

    override fun getItemCount(): Int {
        return titles.size
    }

    fun setTitles(list:List<String>){
        titles.clear()
        titles.addAll(list)
    }

    inner class FavViewHolder(val binding: RowFavChipsBinding, val mCtx:Context) : RecyclerView.ViewHolder(binding.chipValue){
        fun bind(title:String){
            binding.chipValue.text = title
            binding.chipValue.setOnClickListener {
                binding.chipValue.isChecked = true
            }
        }
    }

}
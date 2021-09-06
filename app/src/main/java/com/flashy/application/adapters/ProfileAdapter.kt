package com.flashy.application.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.flashy.application.R
import com.flashy.application.models.ProfileInfo
import com.flashy.application.viewholders.ProfileVH

class ProfileAdapter(var mCtx:Context, var profileList:ArrayList<ProfileInfo>) : RecyclerView.Adapter<ProfileVH>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProfileVH {
        return ProfileVH(LayoutInflater.from(mCtx).inflate(R.layout.row_profile_attributes, parent, false))
    }

    override fun onBindViewHolder(holder: ProfileVH, position: Int) {
        bindItem(holder, position)
    }
    //return number of items
    override fun getItemCount(): Int =  profileList.size

    private fun bindItem(holder: ProfileVH, position: Int){
        val profileInfo = profileList[position]
        //add attributes from list
        holder.txtTitle.text = profileInfo.title
        holder.txtDescription.text = profileInfo.description
        holder.imgNext.setOnClickListener {

        }
    }

}
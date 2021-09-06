package com.flashy.application.viewholders

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.flashy.application.R

class ProfileVH(var itemView: View) : RecyclerView.ViewHolder(itemView) {
    var txtTitle: TextView = itemView.findViewById(R.id.txt_profile_title)
    var txtDescription: TextView = itemView.findViewById(R.id.txt_profile_details)
    var imgNext: ImageView = itemView.findViewById(R.id.img_open_profile)
}
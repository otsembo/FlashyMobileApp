package com.flashy.application.viewmodels.home

import androidx.lifecycle.ViewModel
import com.flashy.application.general.AppUtil
import com.flashy.application.general.ProfileUtil
import com.flashy.application.models.ProfileInfo

class ProfileViewModel : ViewModel() {

    //arraylist of information
    var profileInformation: ArrayList<ProfileInfo> = ArrayList()

    init {
        //initialize data at runtime
        initData()
    }

    //add data to information
    private fun initData(){
        for (i in ProfileUtil.titles.indices){
            val info = ProfileInfo(ProfileUtil.titles[i], ProfileUtil.descriptions[i], i)
            //add info to array list
            profileInformation.add(info)
        }
    }

}
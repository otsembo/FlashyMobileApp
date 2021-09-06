package com.flashy.application.fragments.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.flashy.application.R
import com.flashy.application.adapters.ProfileAdapter
import com.flashy.application.databinding.FragmentHomeProfileBinding
import com.flashy.application.viewmodels.authentication.LoginViewModel
import com.flashy.application.viewmodels.home.ProfileViewModel

class Profile : Fragment() {

    private lateinit var binding: FragmentHomeProfileBinding

    private val viewModel by lazy{
        ViewModelProvider(this).get(ProfileViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //initialize binding
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home_profile, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //set recyclerview properties
        binding.apply {
            rvProfileAttributes.addItemDecoration(DividerItemDecoration(requireActivity(), DividerItemDecoration.VERTICAL))
            //Adapter
            val adapter = ProfileAdapter(requireActivity(), viewModel.profileInformation)
            //attach adapter to recycler view
            rvProfileAttributes.adapter = adapter
        }

    }

}
package com.flashy.application.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.flashy.application.R
import com.flashy.application.databinding.ActivityHomeBinding
import com.flashy.application.general.AppUtil
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeActivity : AppCompatActivity() {

    private lateinit var navController  :NavController
    private lateinit var navHostFragment:NavHostFragment
    private lateinit var binding        :ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //initialize UI
        initUI()
        //initialize DB
        initDB()

    }

    private fun initUI(){
        //initialize binding
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home)
        //initialize views
        binding.apply {
            navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment_home) as NavHostFragment
            navController = navHostFragment.navController
            mainBottomNav.setupWithNavController(navController)
        }
    }

    private fun initDB(){
        CoroutineScope(Dispatchers.IO).launch {
            AppUtil.initDB(this@HomeActivity)
        }
    }

}
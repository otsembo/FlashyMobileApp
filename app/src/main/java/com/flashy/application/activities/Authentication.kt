package com.flashy.application.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.flashy.application.R

class Authentication : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_authentication)

        //initialize navigation host fragment
        val navHostFragment = supportFragmentManager.
            findFragmentById(R.id.nav_host_fragment_auth) as NavHostFragment

        val navController = navHostFragment.findNavController()

    }
}
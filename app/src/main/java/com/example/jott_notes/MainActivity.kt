package com.example.jott_notes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupActionBarWithNavController

class MainActivity : AppCompatActivity() {

    private lateinit var navController:NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)




//        setSupportActionBar(findViewById(R.id.toolbar))

        navController = findNavController(R.id.fragmentContainerView)
//        setupActionBarWithNavController(navController)


    }

    override fun onNavigateUp(): Boolean {
        return  navController.navigateUp() || super.onNavigateUp()
    }
}
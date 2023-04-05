package com.romka_po.driveassistant

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.romka_po.driveassistant.adapters.FBTools
import com.romka_po.driveassistant.adapters.VKTools
import com.romka_po.driveassistant.databinding.ActivityMainBinding
import com.romka_po.driveassistant.repositories.FBRepository


class MainActivity : AppCompatActivity(){

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        VKLOGIN.setVKStart(this)
        FBRepository(FBTools(), VKTools()).registerVKAuthProvider(this)
        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)

        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            if (destination.id == R.id.navigation_login ||
                destination.id == R.id.navigation_splash ||
                destination.id == R.id.navigation_register)
                navView.visibility = View.GONE
            else
                navView.visibility = View.VISIBLE
        }
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.

        navView.setupWithNavController(navController)

    }
}
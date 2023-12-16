package com.app.muscu3000

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.app.muscu3000.database.AppDatabase
import com.app.muscu3000.databinding.ActivityMainBinding
import com.app.muscu3000.model.User
import com.google.android.material.bottomnavigation.BottomNavigationItemView
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    private lateinit var navBar: BottomNavigationView

    companion object {
        lateinit var database: AppDatabase
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        (supportFragmentManager.findFragmentById(R.id.navHostFragmentContainerView) as NavHostFragment).also {
            navController = it.navController
        }

        navBar = findViewById(R.id.bottomNavigationView)
        navBar.setOnItemSelectedListener {
            when(it.itemId){
                R.id.homeNavIcon -> {
                    navController.navigate(R.id.homeFragment)
                    true
                }
                R.id.profileNavIcon -> {
                    navController.navigate(R.id.profileFragment)
                    true
                }
                R.id.addSessionNavIcon -> {
                    navController.navigate(R.id.addGymSessionFragment)
                    true
                }
                else -> false
            }
        }

        // Initialize the Room database
        database = AppDatabase.getInstance(this)!!

    }
}

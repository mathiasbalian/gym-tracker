package com.app.muscu3000

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import com.app.muscu3000.database.AppDatabase
import com.app.muscu3000.model.User
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    companion object {
        lateinit var database: AppDatabase
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize the Room database
        database = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "app_database"
        ).build()

        // Assuming you have an initialized database instance (MainActivity.database)
        var userDao = database.userDao()

        val user = User(
            email = "test@example.com",
            password = "password123",
            fullName = "Test User"
        )
        // Launch a coroutine to insert the user into the database
        lifecycleScope.launch {
            userDao.insertUser(user)
        }

        // Assuming you have an initialized database instance (MainActivity.database)
        userDao = database.userDao()

        // Launch a coroutine to retrieve the user by their ID
        lifecycleScope.launch {
            val retrievedUser = userDao.getUserById(1) // Assuming 1 is the ID of the user you inserted

            if (retrievedUser != null) {
                // Data retrieval was successful
                // Log or display the retrieved user's information
                Log.d("MainActivity", "Retrieved User: $retrievedUser")
            } else {
                // User not found or an error occurred
                Log.e("MainActivity", "Failed to retrieve user")
            }
        }


    }
}

package com.app.muscu3000.fragments

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.app.muscu3000.R
import com.app.muscu3000.dao.UserDao
import com.app.muscu3000.database.AppDatabase

class ProfileFragment : Fragment(R.layout.profile_fragment) {

    private lateinit var sharedPreferences: SharedPreferences

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val userName: TextView = view.findViewById(R.id.userFullNameTextView)

        sharedPreferences = requireActivity().getPreferences(Context.MODE_PRIVATE)
        val logOutButton: Button = view.findViewById(R.id.logOutButton)
        logOutButton.setOnClickListener {
            sharedPreferences.edit().putBoolean("is_logged_in", false).apply()
            findNavController().navigate(R.id.loginFragment)
        }

        val internetButton: ImageButton = view.findViewById(R.id.internetPageButton)
        internetButton.setOnClickListener {
            findNavController().navigate(R.id.internetFragment)
        }
    }

}
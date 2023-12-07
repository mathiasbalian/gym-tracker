package com.app.muscu3000.fragments

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.app.muscu3000.R
import com.app.muscu3000.dao.UserDao
import com.app.muscu3000.database.AppDatabase

class ProfileFragment : Fragment(R.layout.profile_fragment) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val userName: TextView = view.findViewById(R.id.userFullNameTextView)


        val logOutButton: Button = view.findViewById(R.id.logOutButton)
        logOutButton.setOnClickListener {

        }

        val internetButton: Button = view.findViewById(R.id.internetPageButton)
        internetButton.setOnClickListener {
            //findNavController().navigate(R.id.action_profileFragment_to_internetFragment)
        }
    }

}
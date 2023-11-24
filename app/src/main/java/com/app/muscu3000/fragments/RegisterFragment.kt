package com.app.muscu3000.fragments

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.app.muscu3000.R

class RegisterFragment : Fragment(R.layout.register_fragment) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Find the register button
        val loginButton: Button = view.findViewById(R.id.loginBTN)

        // Set a click listener on the register button
        loginButton.setOnClickListener {
            // Navigate to the RegisterFragment when the button is clicked
            findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
        }
    }
}
package com.app.muscu3000.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.app.muscu3000.R


class LoginFragment : Fragment(R.layout.login_fragment) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Find the login and register buttons
        val loginButton: Button = view.findViewById(R.id.loginBTN)
        val registerButton: Button = view.findViewById(R.id.registerBTN)

        // Set a click listener on the login button
        loginButton.setOnClickListener {
            // Perform credential verification here
            if (isCredentialsValid()) {
                // If credentials are valid, navigate to the next screen or perform the desired action
                // For now, let's navigate to a success fragment as an example

                // DO THE NAVIGATION
                //findNavController().navigate(R.id.action_loginFragment_to_successFragment)
            } else {
                // If credentials are not valid, show an error message or handle accordingly
                // For now, let's just log an error message
                Log.e("LoginFragment", "Invalid credentials")
            }
        }

        // Set a click listener on the register button
        registerButton.setOnClickListener {
            // Navigate to the RegisterFragment when the button is clicked
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }
    }


    private fun isCredentialsValid(): Boolean {
        // Replace with your actual credential verification logic
        val emailInput: EditText = view?.findViewById(R.id.emailInput) ?: return false
        val passwordInput: EditText = view?.findViewById(R.id.passwordInput) ?: return false

        val email = emailInput.text.toString().trim()
        val password = passwordInput.text.toString().trim()

        return email == "admin" && password == "admin"
    }

}
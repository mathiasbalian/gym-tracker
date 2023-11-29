package com.app.muscu3000.fragments

import android.content.Context
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.RelativeLayout
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

        // Set up a touch listener on the parent layout to hide the keyboard when touched outside the input fields
        val parentLayout: RelativeLayout = view.findViewById(R.id.registerFragment)
        parentLayout.setOnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_DOWN) {
                hideKeyboard()
            }
            false
        }
    }


    private fun hideKeyboard() {
        val imm = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view?.windowToken, 0)
    }
}
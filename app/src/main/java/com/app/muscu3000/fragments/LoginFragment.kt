package com.app.muscu3000.fragments

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.RelativeLayout
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.app.muscu3000.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout


class LoginFragment : Fragment(R.layout.login_fragment) {

    private lateinit var sharedPreferences: SharedPreferences
    companion object {
        private const val KEY_IS_LOGGED_IN = "is_logged_in"
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
        // Access BottomNavigationView from activity's layout
        val bottomNavigationView: BottomNavigationView? = activity?.findViewById(R.id.bottomNavigationView)

        // Check if the BottomNavigationView is found
        if (bottomNavigationView != null) {
            // Set visibility to GONE
            bottomNavigationView.visibility = View.GONE
        } else {
            Log.e("LoginFragment", "BottomNavigationView not found in activity's layout")
        }

        // Initialize SharedPreferences
        sharedPreferences = requireActivity().getPreferences(Context.MODE_PRIVATE)

        // Check if the user is already logged in
        if (isLoggedIn()) {
            Log.d("test1", "ALREADY LOGGED")

            if (bottomNavigationView != null) {
                bottomNavigationView.visibility = View.VISIBLE
            }
            // If the user is already logged in, navigate to the success fragment
            findNavController().navigate(R.id.homeFragment)
            return
        }

        // Find the login and register buttons
        val loginButton: Button = view.findViewById(R.id.loginBTN)
        val registerButton: Button = view.findViewById(R.id.registerBTN)

        // Set a click listener on the login button
        loginButton.setOnClickListener {

            // Perform credential verification here
            if (isCredentialsValid()) {
                // If credentials are valid, mark the user as logged in

                setLoggedIn(true)

                // Navigate to the success fragment
                if (bottomNavigationView != null) {
                    bottomNavigationView.visibility = View.VISIBLE
                }
                findNavController().navigate(R.id.homeFragment)

            } else {
                // If credentials are not valid, show an error message or handle accordingly
                showErrorDialog("Credentials incorrect")
            }
        }

        // Set a click listener on the register button
        registerButton.setOnClickListener {
            // Navigate to the RegisterFragment when the button is clicked
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }

        // Set up a touch listener on the parent layout to hide the keyboard when touched outside the input fields
        val parentLayout: RelativeLayout = view.findViewById(R.id.loginFragment)
        parentLayout.setOnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_DOWN) {
                hideKeyboard()
            }
            false
        }
    }

    private fun isLoggedIn(): Boolean {
        // Check the login status from SharedPreferences
        return sharedPreferences.getBoolean(KEY_IS_LOGGED_IN, false)
    }

    private fun setLoggedIn(isLoggedIn: Boolean) {
        // Set the login status in SharedPreferences
        sharedPreferences.edit().putBoolean(KEY_IS_LOGGED_IN, isLoggedIn).apply()
    }


    private fun isCredentialsValid(): Boolean {
        // Find the TextInputLayouts
        val emailInputLayout: TextInputLayout? = view?.findViewById(R.id.emailInputLayout)
        val passwordInputLayout: TextInputLayout? = view?.findViewById(R.id.passwordInputLayout)

        // Find the TextInputEditTexts inside TextInputLayouts
        val emailInput: TextInputEditText? = emailInputLayout?.findViewById(R.id.emailInputEditText)
        val passwordInput: TextInputEditText? = passwordInputLayout?.findViewById(R.id.passwordInputEditText)

        // Check for null to avoid ClassCastException
        if (emailInput == null || passwordInput == null) {
            return false
        }

        val email = emailInput.text.toString().trim()
        val password = passwordInput.text.toString().trim()

        return email == "admin" && password == "admin"
    }

    private fun showErrorDialog(errorMessage: String) {
        val dialogFragment = DialogFragment.newInstance(errorMessage)
        dialogFragment.show(parentFragmentManager, "ErrorDialogFragment")
    }

    private fun hideKeyboard() {
        val imm = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view?.windowToken, 0)
    }
}
package com.app.muscu3000.fragments

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class DialogFragment : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val errorMessage = arguments?.getString(ARG_ERROR_MESSAGE)

        return MaterialAlertDialogBuilder(requireContext())
            .setTitle("Error")
            .setMessage(errorMessage)
            .setPositiveButton("OK") { _, _ ->
                dismiss()
            }
            .create()
    }

    companion object {
        private const val ARG_ERROR_MESSAGE = "error_message"

        fun newInstance(errorMessage: String): com.app.muscu3000.fragments.DialogFragment {
            val fragment = com.app.muscu3000.fragments.DialogFragment()
            val args = Bundle()
            args.putString(ARG_ERROR_MESSAGE, errorMessage)
            fragment.arguments = args
            return fragment
        }
    }
}

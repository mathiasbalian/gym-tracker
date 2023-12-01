package com.app.muscu3000.fragments

import android.app.DatePickerDialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.DatePicker
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.muscu3000.Adapter.GymSessionAdapter
import com.app.muscu3000.R
import com.app.muscu3000.model.Exercice
import com.app.muscu3000.model.ExerciceInfos
import com.app.muscu3000.model.GymSet
import com.google.android.material.textfield.TextInputEditText
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class AddGymSessionFragment : Fragment(R.layout.add_gym_session_fragment), DatePickerDialog.OnDateSetListener {

    private val exerciceList = mutableListOf<ExerciceInfos>()
    private lateinit var adapter: GymSessionAdapter
    private lateinit var addExerciceButton: Button
    private lateinit var dateEditText: TextInputEditText
    private val calendar: Calendar = Calendar.getInstance()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // doesn't work
        dateEditText = view.findViewById(R.id.dateEditText)
        dateEditText.setOnClickListener {
            Log.d("ok", "okkkk")
            showDatePickerDialog()
        }

        // Initialize RecyclerView and its adapter
        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerView)
        adapter = GymSessionAdapter(exerciceList)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        // Set up the "Add Exercice" button click listener
        addExerciceButton = view.findViewById(R.id.addExeciceButton)
        addExerciceButton.setOnClickListener {
            // Generate a new GymSet and add it to the adapter
            val newExercice = ExerciceInfos(
                exercice = Exercice(name = "", description = ""),
                listGymSet = mutableListOf(GymSet(nbRep = 0, weight = 0.0))
            )
            adapter.addExercice(newExercice)
        }

        // Set up a touch listener on the parent layout to hide the keyboard when touched outside the input fields
        val parentLayout: ConstraintLayout = view.findViewById(R.id.addGymSessionFragment)
        parentLayout.setOnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_DOWN) {
                hideKeyboard()
            }
            false
        }
    }

    private fun showDatePickerDialog() {
        val datePickerDialog = DatePickerDialog(
            requireContext(),
            this@AddGymSessionFragment, // Specify the listener explicitly
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
        datePickerDialog.show()
    }


    override fun onDateSet(view: DatePicker?, year: Int, monthOfYear: Int, dayOfMonth: Int) {
        calendar.set(Calendar.YEAR, year)
        calendar.set(Calendar.MONTH, monthOfYear)
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)

        updateDateEditText()
    }

    private fun updateDateEditText() {
        val dateFormat = SimpleDateFormat("MM/dd/yyyy", Locale.US)
        dateEditText.setText(dateFormat.format(calendar.time))
    }

    private fun hideKeyboard() {
        val imm = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view?.windowToken, 0)
    }
}

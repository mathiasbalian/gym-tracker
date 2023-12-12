package com.app.muscu3000.fragments

import android.content.Context
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.muscu3000.Adapter.GymSessionAdapter
import com.app.muscu3000.R
import com.app.muscu3000.model.Exercise
import com.app.muscu3000.model.ExerciseInfos
import com.app.muscu3000.model.GymSession
import com.app.muscu3000.model.GymSet
import com.app.muscu3000.viewmodels.GymSessionsViewModel
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener
import com.google.android.material.textfield.TextInputEditText
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class AddGymSessionFragment : Fragment(R.layout.add_gym_session_fragment) {

    private val exerciseList = mutableListOf<ExerciseInfos>()
    private lateinit var adapter: GymSessionAdapter
    private lateinit var addExerciseButton: Button
    private lateinit var validateButton: Button
    private lateinit var backButton: Button
    private lateinit var dateEditText: TextInputEditText
    private lateinit var sessionNameEditText: TextInputEditText
    private val gymSessionViewModel: GymSessionsViewModel by activityViewModels()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initialize(view)

        setupListeners()

        hideKeyboard(view)

    }

    private fun initialize(view: View) {
        sessionNameEditText = view.findViewById(R.id.sessionNameEditText)
        dateEditText = view.findViewById(R.id.dateEditText)
        addExerciseButton = view.findViewById(R.id.addExeciceButton)
        validateButton = view.findViewById(R.id.validateButton)
        backButton = view.findViewById(R.id.backButton)

        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerView)
        adapter = GymSessionAdapter(exerciseList)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
    }
    private fun setupListeners() {

        backButton.setOnClickListener {
            findNavController().navigate(R.id.homeFragment)
        }
        validateButton.setOnClickListener {
            // Initialize the Room database
            val exerciseInfos = adapter.getExerciseInfo()
            val date = dateEditText.text.toString()
            val sessionName = sessionNameEditText.text.toString()
            val duration = 5
            val difficulty = ""

            gymSessionViewModel.addGymSession(GymSession(date = date, duration = duration, difficulty = difficulty, name = sessionName), exerciseInfos)
            findNavController().navigate(R.id.homeFragment)

        }

        addExerciseButton.setOnClickListener {
            // Generate a new GymSet and add it to the adapter
            val newExercise = ExerciseInfos(
                exercise = Exercise(exerciseName = "", description = ""),
                listGymSet = mutableListOf(GymSet(nbRep = 0, weight = 0.0, setNumber = 0))
            )
            adapter.addExercise(newExercise)
        }

        // Initialize MaterialDatePicker
        val builder = MaterialDatePicker.Builder.datePicker().setTheme(R.style.MyDatePickerTheme)
        builder.setSelection(Calendar.getInstance().timeInMillis)
        val datePicker = builder.build()

        // Set listener for positive button click (when date is selected)
        datePicker.addOnPositiveButtonClickListener(object :
            MaterialPickerOnPositiveButtonClickListener<Long> {
            override fun onPositiveButtonClick(selection: Long) {
                val selectedDate = Date(selection)
                updateDateEditText(selectedDate)
            }
        })
        dateEditText.setOnClickListener {
            datePicker.show(requireFragmentManager(), datePicker.toString())
        }
    }
    private fun hideKeyboard(view: View) {
        System.out.println("ok")
        // Set up a touch listener on the parent layout to hide the keyboard when touched outside the input fields
        val parentLayout: ConstraintLayout = view.findViewById(R.id.addGymSessionFragment)
        parentLayout.setOnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_DOWN) {
                val imm = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(view?.windowToken, 0)
            }
            false
        }

    }
    private fun updateDateEditText(selectedDate: Date) {
        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.FRANCE)
        dateEditText.setText(dateFormat.format(selectedDate))
    }

}

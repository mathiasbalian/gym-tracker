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
import kotlinx.coroutines.coroutineScope
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class AddGymSessionFragment : Fragment(R.layout.add_gym_session_fragment) {

    private val exerciseList = mutableListOf<ExerciseInfos>()
    private lateinit var adapter: GymSessionAdapter
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
        validateButton = view.findViewById(R.id.validateButton)
        backButton = view.findViewById(R.id.backButton)

        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerView)
        adapter = GymSessionAdapter(exerciseList)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())


        for (i in 0 until 10) {
            val newExercise = ExerciseInfos(
                exercise = Exercise(exerciseName = "", description = ""),
                listGymSet = mutableListOf(GymSet(nbRep = 0, weight = 0.0, setNumber = 1),
                    GymSet(nbRep = 0, weight = 0.0, setNumber = 2),
                    GymSet(nbRep = 0, weight = 0.0, setNumber = 3),
                    GymSet(nbRep = 0, weight = 0.0, setNumber = 4))
            )
            adapter.addExercise(newExercise)
        }

    }
    private fun setupListeners() {

        backButton.setOnClickListener {
            findNavController().navigate(R.id.homeFragment)
        }
        validateButton.setOnClickListener {
            // Initialize the Room database
            val exerciseInfos = adapter.getExerciseInfo()
            val finalExercises : MutableList<ExerciseInfos> = mutableListOf()
            System.out.println(exerciseInfos)

            for (exerciseInfo in exerciseInfos) {
                if (exerciseInfo.exercise.exerciseName != "") {
                    System.out.println(exerciseInfo.exercise.exerciseName)

                    val exerciseName = exerciseInfo.exercise.exerciseName
                    var listGymSet: MutableList<GymSet> = mutableListOf()
                    for (gymSet in exerciseInfo.listGymSet) {
                        if (gymSet.nbRep != 0 && gymSet.weight != 0.0) {
                            listGymSet.add(gymSet)
                        }
                    }
                    val newExercise = ExerciseInfos(
                        exercise = Exercise(exerciseName = exerciseName, description = ""),
                        listGymSet = listGymSet
                    )
                    finalExercises.add(newExercise)
                }
            }

            for (exercise in finalExercises) {
                System.out.println(exercise.exercise)
                System.out.println(exercise.listGymSet)
            }

            val date = dateEditText.text.toString()
            val sessionName = sessionNameEditText.text.toString()
            val duration = 5
            val difficulty = ""

            gymSessionViewModel.addGymSession(date = date, duration = duration, difficulty = difficulty, name = sessionName, finalExercises)
            findNavController().navigate(R.id.homeFragment)

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

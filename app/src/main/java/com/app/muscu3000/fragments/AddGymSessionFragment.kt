package com.app.muscu3000.fragments

import android.content.Context
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.muscu3000.Adapter.GymSessionAdapter
import com.app.muscu3000.MainActivity
import com.app.muscu3000.R
import com.app.muscu3000.model.Exercice
import com.app.muscu3000.model.ExerciceGymSet
import com.app.muscu3000.model.ExerciceInfos
import com.app.muscu3000.model.GymSession
import com.app.muscu3000.model.GymSessionExercice
import com.app.muscu3000.model.GymSet
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class AddGymSessionFragment : Fragment(R.layout.add_gym_session_fragment) {

    private val exerciceList = mutableListOf<ExerciceInfos>()
    private lateinit var adapter: GymSessionAdapter
    private lateinit var addExerciceButton: Button
    private lateinit var validateButton: Button
    private lateinit var dateEditText: TextInputEditText
    private lateinit var sessionNameEditText: TextInputEditText

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sessionNameEditText = view.findViewById(R.id.sessionNameEditText)

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

        dateEditText = view.findViewById(R.id.dateEditText)
        // Show the date picker when the dateEditText is clicked
        dateEditText.setOnClickListener {
            datePicker.show(requireFragmentManager(), datePicker.toString())
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
                date = "",
                listGymSet = mutableListOf(GymSet(nbRep = 0, weight = 0.0, setNumber = 0))
            )
            adapter.addExercice(newExercice)
        }

        // Set up the "Validate button" button click listener
        validateButton = view.findViewById(R.id.validateButton)
        validateButton.setOnClickListener {
            // Initialize the Room database
            val exerciseInfos = adapter.getExerciceInfo()
            val date = dateEditText.text.toString()
            val sessionName = sessionNameEditText.text.toString()
            val duration = 5
            val difficulty = ""

            val gymSessionDao = MainActivity.database.gymSessionDao()
            lifecycleScope.launch {
                // Insert GymSession
                val gymSessionId = gymSessionDao.insertGymSession(GymSession(date = date, duration = duration, difficulty = difficulty, name = sessionName))

                for (exerciseInfo in exerciseInfos) {
                    // DAO
                    val exerciceDao = MainActivity.database.exerciceDao()
                    val gymSetDao = MainActivity.database.gymSetDao()
                    val exerciceGymSetDao = MainActivity.database.exerciceGymSetDao()
                    val gymSessionExerciceDao = MainActivity.database.gymSessionExerciceDao()

                    // Insertions
                    val exerciceId = exerciceDao.insertExercice(exerciseInfo.exercice)

                    gymSessionExerciceDao.insertGymSessionExercice(GymSessionExercice(gymSessionId, exerciceId))

                    for (gymSet in exerciseInfo.listGymSet) {
                        val gymSetId = gymSetDao.insertGymSet(gymSet)

                        exerciceGymSetDao.insertExerciceGymSet(ExerciceGymSet(exerciceId, gymSetId))
                    }
                }
                displayDatabaseContents()
                findNavController().navigate(R.id.homeFragment)
            }
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


    suspend fun displayDatabaseContents() {
        val gymSessionDao = MainActivity.database.gymSessionDao()
        val exerciceDao = MainActivity.database.exerciceDao()
        val gymSetDao = MainActivity.database.gymSetDao()
        val exerciceGymSetDao = MainActivity.database.exerciceGymSetDao()
        val gymSessionExerciceDao = MainActivity.database.gymSessionExerciceDao()

        // Display GymSessions
        val gymSessions = gymSessionDao.getAllGymSessions()
        for (gymSession in gymSessions) {
            println("GymSession: $gymSession")
        }

        // Display Exercises
        val exercises = exerciceDao.getAllExercises()
        for (exercise in exercises) {
            println("Exercise: $exercise")
        }

        // Display GymSets
        val gymSets = gymSetDao.getAllGymSets()
        for (gymSet in gymSets) {
            println("GymSet: $gymSet")
        }

        // Display ExerciceGymSets
        val exerciceGymSets = exerciceGymSetDao.getAllExerciceGymSets()
        for (exerciceGymSet in exerciceGymSets) {
            println("ExerciceGymSet: $exerciceGymSet")
        }

        // Display GymSessionExercices
        val gymSessionExercices = gymSessionExerciceDao.getAllGymSessionExercices()
        for (gymSessionExercice in gymSessionExercices) {
            println("GymSessionExercice: $gymSessionExercice")
        }
    }

    private fun hideKeyboard() {
        val imm = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view?.windowToken, 0)
    }
    private fun updateDateEditText(selectedDate: Date) {
        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.FRANCE)
        dateEditText.setText(dateFormat.format(selectedDate))
    }

}

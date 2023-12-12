package com.app.muscu3000.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.allViews
import androidx.core.view.children
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.app.muscu3000.MainActivity
import com.app.muscu3000.R
import com.app.muscu3000.model.Exercise
import com.app.muscu3000.model.GymSession
import com.app.muscu3000.model.GymSet
import com.app.muscu3000.viewmodels.GymSessionsViewModel
import androidx.navigation.fragment.findNavController
import com.app.muscu3000.fragments.EditGymSessionFragment
import com.app.muscu3000.fragments.HomeFragment
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception

class HomeGymSessionAdapter(
    private val navController: NavController,
    private val sessionList: MutableList<GymSession>,
    private val gymSessionsViewModel: GymSessionsViewModel):
    RecyclerView.Adapter<HomeGymSessionAdapter.GymSessionHolder>() {
        inner class GymSessionHolder(view: View) : RecyclerView.ViewHolder(view){
            private val sessionDetails: TextView
            private val editImageView: ImageView
            private val expandImageView: ImageView
            private var sessionExercises: List<Exercise> = ArrayList()
            private var exerciseSets: List<GymSet> = ArrayList()
            private var isExpanded: Boolean
            //private lateinit var setLayout: LinearLayout
            //private lateinit var exerciseLayout: LinearLayout
            private val gymSessionLayout: LinearLayout
            init {
                sessionDetails = view.findViewById(R.id.sessionDetailsTxt)
                editImageView = view.findViewById(R.id.editIv)
                expandImageView = view.findViewById(R.id.expandIv)
                isExpanded = false
                gymSessionLayout = view.findViewById(R.id.linearGymSessionHolder)
            }

            fun onBind(gymSession: GymSession){
                sessionDetails.text = "${gymSession.name} ${gymSession.date}"

                editImageView.setOnClickListener {
                    gymSessionsViewModel.setSelectedSession(gymSession)
                    navController.navigate(R.id.editGymSessionFragment)
                }

                expandImageView.setOnClickListener {
                    if(!isExpanded){
                        CoroutineScope(Dispatchers.IO).launch{
                            sessionExercises = MainActivity.database.exerciseDao().getExercisesBySessionId(gymSession.gymSessionId)

                            for(exercise in sessionExercises){
                                exerciseSets = MainActivity.database.gymSetDao().getGymSetsByExerciseId(exercise.exerciseId)
                                withContext(Dispatchers.Main){
                                    val exerciseLayout = createExerciseLayout(exercise)
                                    gymSessionLayout.addView(exerciseLayout)
                                    for(set in exerciseSets){
                                        withContext(Dispatchers.Main){
                                            val setLayout = createSetLayout(set)
                                            exerciseLayout.addView(setLayout)
                                        }
                                    }
                                }


                            }
                            isExpanded = true
                            withContext(Dispatchers.Main){
                                notifyDataSetChanged()
                            }
                        }
                    }
                    else{
                        gymSessionLayout.removeViews(1, sessionExercises.size)
                        isExpanded = false
                    }
                }
            }

            private fun createExerciseLayout(exercise: Exercise): LinearLayout {
                val exerciseLayoutInflater = LayoutInflater.from(itemView.context)
                val exerciseLayout = exerciseLayoutInflater.inflate(R.layout.exercise_holder_no_add_button, null) as LinearLayout
                exerciseLayout.findViewById<TextInputEditText>(R.id.exerciseNameEditText).setText(exercise.exerciseName)
                return exerciseLayout
            }

            private fun createSetLayout(gymSet: GymSet): LinearLayout {
                val setLayoutInflater = LayoutInflater.from(itemView.context)
                val setLayout = setLayoutInflater.inflate(R.layout.set_holder, null) as LinearLayout

                setLayout.findViewById<TextView>(R.id.setNumberTextView).text = "Set ${gymSet.setNumber + 1}"

                setLayout.findViewById<TextInputEditText>(R.id.nbRepsEditText)
                    .setText(if (gymSet.nbRep == 0) "" else gymSet.nbRep.toString())

                setLayout.findViewById<TextInputEditText>(R.id.weightEditText)
                    .setText(if (gymSet.weight == 0.0) "" else gymSet.weight.toString())

                return setLayout
            }

        }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GymSessionHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.gym_session_holder, parent, false)
        return GymSessionHolder(view)
    }

    override fun getItemCount(): Int = sessionList.size

    override fun onBindViewHolder(holder: GymSessionHolder, position: Int) {
        holder.onBind(sessionList[position])
    }
}
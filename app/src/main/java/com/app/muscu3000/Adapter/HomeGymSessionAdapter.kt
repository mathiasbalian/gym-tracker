package com.app.muscu3000.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

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
            private val isExpanded: Boolean

            init {
                sessionDetails = view.findViewById(R.id.sessionDetailsTxt)
                editImageView = view.findViewById(R.id.editIv)
                expandImageView = view.findViewById(R.id.expandIv)
                isExpanded = false
            }

            fun onBind(gymSession: GymSession){
                sessionDetails.text = "${gymSession.name} ${gymSession.date}"

                editImageView.setOnClickListener {
                    gymSessionsViewModel.setSelectedSession(gymSession)
                    navController.navigate(R.id.editGymSessionFragment)
                }

                expandImageView.setOnClickListener {
                    CoroutineScope(Dispatchers.IO).launch{
                        sessionExercises = MainActivity.database.exerciseDao().getExercisesBySessionId(gymSession.gymSessionId)
                        for(exercise in sessionExercises){
                            exerciseSets = MainActivity.database.gymSetDao().getGymSetsByExerciseId(exercise.exerciseId)
                        }
                    }
                }
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
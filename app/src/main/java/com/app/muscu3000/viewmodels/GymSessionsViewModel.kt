package com.app.muscu3000.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.muscu3000.MainActivity
import com.app.muscu3000.model.Exercise
import com.app.muscu3000.model.ExerciseGymSet
import com.app.muscu3000.model.ExerciseInfos
import com.app.muscu3000.model.GymSession
import com.app.muscu3000.model.GymSessionExercise
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Date

class GymSessionsViewModel : ViewModel() {

    private var _sessionsList: MutableLiveData<MutableList<GymSession>> = MutableLiveData()
    private var selectedSession: MutableLiveData<GymSession> = MutableLiveData()

    val sessionList: LiveData<MutableList<GymSession>>
        get() = _sessionsList

    fun setSelectedSession(gymSession: GymSession){
        selectedSession.value = gymSession
    }

    fun getSelectedSession(): MutableLiveData<GymSession> {
        return this.selectedSession
    }
    init {
        initList()
    }

    private fun initList(){
        viewModelScope.launch(Dispatchers.IO) {
            val result = MainActivity.database.gymSessionDao().getAllGymSessions()

            withContext(Dispatchers.Main) {
                _sessionsList.value = result
            }
        }
    }

    fun updateGymSession(gymSession: GymSession, exerciseInfos: MutableList<ExerciseInfos>) {
        viewModelScope.launch(Dispatchers.IO) {
            val gymSessionDao = MainActivity.database.gymSessionDao()
            val exerciseDao = MainActivity.database.exerciseDao()
            val gymSetDao = MainActivity.database.gymSetDao()
            val exerciseGymSetDao = MainActivity.database.exerciseGymSetDao()
            val gymSessionExerciseDao = MainActivity.database.gymSessionExerciseDao()

            try {
                gymSessionDao.updateGymSession(gymSession)

                for (exerciseInfo in exerciseInfos) {
                    val existingExercise = exerciseDao.getExerciseById(exerciseInfo.exercise.exerciseId)

                    if (existingExercise != null) {
                        // Update existing exercise
                        exerciseDao.updateExercise(exerciseInfo.exercise)
                    } else {
                        // Insert new exercise
                        val exerciseId = exerciseDao.insertExercise(exerciseInfo.exercise)

                        gymSessionExerciseDao.insertGymSessionExercise(GymSessionExercise(gymSession.gymSessionId, exerciseId))
                    }

                    // Update or insert gym set data
                    for (gymSet in exerciseInfo.listGymSet) {
                        val existingGymSet = gymSetDao.getGymSetById(gymSet.setId)

                        if (existingGymSet != null) {
                            gymSetDao.updateGymSet(gymSet)
                        } else {
                            val gymSetId = gymSetDao.insertGymSet(gymSet)

                            exerciseGymSetDao.insertExerciseGymSet(ExerciseGymSet(exerciseInfo.exercise.exerciseId, gymSetId))
                        }
                    }
                }
                val updatedSessionsList = gymSessionDao.getAllGymSessions()
                // Update the list on the main thread
                withContext(Dispatchers.Main) {
                    _sessionsList.value = updatedSessionsList.toMutableList()
                }
            } catch (e: Exception) {
                System.err.println("Exception during GymSession update: $e")
            }
        }
    }



    fun addGymSession(date: String, duration: Int, difficulty: String, name: String, exerciseInfos: MutableList<ExerciseInfos>){
        viewModelScope.launch(Dispatchers.IO) {
            val gymSessionId = MainActivity.database.gymSessionDao().getNextSessionId()

            val gymSession: GymSession = GymSession(gymSessionId, name, date, difficulty, duration)
            MainActivity.database.gymSessionDao().insertGymSession(gymSession)
            for (exerciseInfo in exerciseInfos) {
                // DAO
                val exerciseDao = MainActivity.database.exerciseDao()
                val gymSetDao = MainActivity.database.gymSetDao()
                val exerciseGymSetDao = MainActivity.database.exerciseGymSetDao()
                val gymSessionExerciseDao = MainActivity.database.gymSessionExerciseDao()

                // Insertions
                val exerciseId = exerciseDao.insertExercise(exerciseInfo.exercise)

                gymSessionExerciseDao.insertGymSessionExercise(GymSessionExercise(gymSessionId, exerciseId))
                for (gymSet in exerciseInfo.listGymSet) {
                    val gymSetId = gymSetDao.insertGymSet(gymSet)

                    exerciseGymSetDao.insertExerciseGymSet(ExerciseGymSet(exerciseId, gymSetId))
                }
            }

            withContext(Dispatchers.Main){
                _sessionsList.value = _sessionsList.value?.apply {
                    add(gymSession)
                }
            }
        }
    }

    fun deleteGymSession(gymSessionId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            val deletedGymSession = MainActivity.database.gymSessionDao().getGymSessionById(gymSessionId)

            // Delete gym session and related data
            MainActivity.database.gymSessionDao().deleteGymSessionAndRelatedData(gymSessionId)

            // Update the list on the main thread
            withContext(Dispatchers.Main) {
                _sessionsList.value = _sessionsList.value?.apply {
                    // Remove the deleted gym session from the list
                    deletedGymSession?.let { remove(it) }
                }
            }
        }
    }



}
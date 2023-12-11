package com.app.muscu3000.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.muscu3000.MainActivity
import com.app.muscu3000.model.ExerciseGymSet
import com.app.muscu3000.model.ExerciseInfos
import com.app.muscu3000.model.GymSession
import com.app.muscu3000.model.GymSessionExercise
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class GymSessionsViewModel : ViewModel() {

    private var _sessionsList: MutableLiveData<MutableList<GymSession>> = MutableLiveData()
    private var selectedSession: MutableLiveData<GymSession> = MutableLiveData()

    val sessionList: LiveData<MutableList<GymSession>>
        get() = _sessionsList

    fun setSelectedSession(gymSession: GymSession){
        selectedSession.value = gymSession
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

    fun addGymSession(gymSession: GymSession, exerciseInfos: MutableList<ExerciseInfos>){
        _sessionsList.value = _sessionsList.value?.apply {
            add(gymSession)
        }
        viewModelScope.launch(Dispatchers.IO) {
            val gymSessionId = MainActivity.database.gymSessionDao().insertGymSession(gymSession)
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
        }
    }

    fun updateSelectedGymSession(gymSession: GymSession, exerciseInfos: MutableList<ExerciseInfos>){
        //TODO immplement update gym session logic
    }


}
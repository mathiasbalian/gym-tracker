package com.app.muscu3000.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.app.muscu3000.model.ExerciseGymSet

@Dao
interface ExerciseGymSetDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertExerciseGymSet(exerciseSet: ExerciseGymSet)

    @Query("SELECT * FROM ExerciseGymSet")
    suspend fun getAllExerciseGymSets(): List<ExerciseGymSet>

}
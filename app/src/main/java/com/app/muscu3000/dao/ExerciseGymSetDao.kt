package com.app.muscu3000.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.app.muscu3000.model.Exercise
import com.app.muscu3000.model.ExerciseGymSet
import com.app.muscu3000.model.GymSet

@Dao
interface ExerciseGymSetDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertExerciseGymSet(exerciseSet: ExerciseGymSet)

    @Query("SELECT * FROM ExerciseGymSet")
    suspend fun getAllExerciseGymSets(): List<ExerciseGymSet>

    @Query("SELECT * FROM GymSet WHERE setId IN (SELECT setId FROM ExerciseGymSet WHERE exerciseId = :exerciseId)")
    suspend fun getAllGymSetByExerciseId(exerciseId: Long): MutableList<GymSet>
}
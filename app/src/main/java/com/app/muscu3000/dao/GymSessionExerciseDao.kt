package com.app.muscu3000.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.app.muscu3000.model.Exercise
import com.app.muscu3000.model.GymSessionExercise

@Dao
interface GymSessionExerciseDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGymSessionExercise(gymSessionExercise: GymSessionExercise) : Long

    @Query("SELECT * FROM GymSessionExercise")
    suspend fun getAllGymSessionExercises(): List<GymSessionExercise>

    @Query("SELECT * FROM Exercise WHERE exerciseId IN (SELECT exerciseId FROM GymSessionExercise WHERE gymSessionId = :gymSessionId)")
    suspend fun getAllExercisesBySessionId(gymSessionId: Long): MutableList<Exercise>
}
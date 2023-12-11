package com.app.muscu3000.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.app.muscu3000.model.Exercise

@Dao
interface ExerciseDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertExercise(exercise: Exercise) : Long

    @Query("SELECT * FROM Exercise WHERE exerciseId = :exerciseId")
    suspend fun getExerciseById(exerciseId: Long): Exercise?

    @Query("SELECT * FROM Exercise")
    suspend fun getAllExercises(): List<Exercise>

    @Query("SELECT Exercise.exerciseId, Exercise.exerciseName, Exercise.description FROM Exercise " +
            "JOIN GymSessionExercise on GymSessionExercise.exerciseId = Exercise.exerciseId" +
            " WHERE gymSessionId = :sessionId;")
    suspend fun getExercisesBySessionId(sessionId: Long): List<Exercise>
}
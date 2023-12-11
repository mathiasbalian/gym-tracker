package com.app.muscu3000.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.app.muscu3000.model.GymSessionExercise

@Dao
interface GymSessionExerciseDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGymSessionExercise(gymSessionExercise: GymSessionExercise) : Long

    @Query("SELECT * FROM GymSessionExercise")
    suspend fun getAllGymSessionExercises(): List<GymSessionExercise>
}
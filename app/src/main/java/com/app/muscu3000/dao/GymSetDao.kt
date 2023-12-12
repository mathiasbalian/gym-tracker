package com.app.muscu3000.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.app.muscu3000.model.GymSet

@Dao
interface GymSetDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGymSet(gymSet: GymSet) : Long

    @Query("SELECT * FROM GymSet WHERE setId = :setId")
    suspend fun getGymSetById(setId: Long): GymSet?

    @Query("SELECT * FROM GymSet")
    suspend fun getAllGymSets(): List<GymSet>

    @Query("SELECT GymSet.setId, GymSet.nbRep, GymSet.weight, GymSet.setNumber FROM GymSet " +
            " JOIN ExerciseGymSet ON GymSet.setId = ExerciseGymSet.setId " +
            "WHERE exerciseId = :exerciseId")
    suspend fun getGymSetsByExerciseId(exerciseId: Long): List<GymSet>

    @Update
    suspend fun updateGymSet(gymSet: GymSet)
}
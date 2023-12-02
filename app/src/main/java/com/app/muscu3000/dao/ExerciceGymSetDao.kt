package com.app.muscu3000.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.app.muscu3000.model.ExerciceGymSet

@Dao
interface ExerciceGymSetDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertExerciceGymSet(exerciceSet: ExerciceGymSet)

    @Query("SELECT * FROM ExerciceGymSet")
    suspend fun getAllExerciceGymSets(): List<ExerciceGymSet>

}
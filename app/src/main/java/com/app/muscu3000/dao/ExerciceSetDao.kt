package com.app.muscu3000.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.app.muscu3000.model.ExerciceSet

@Dao
interface ExerciceSetDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertExerciceSet(exerciceSet: ExerciceSet)
}
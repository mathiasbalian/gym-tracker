package com.app.muscu3000.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.app.muscu3000.model.Exercice

@Dao
interface ExerciceDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertExercice(exercice: Exercice)

    @Query("SELECT * FROM Exercice WHERE exerciceId = :exerciceId")
    suspend fun getExerciceById(exerciceId: Long): Exercice?
}
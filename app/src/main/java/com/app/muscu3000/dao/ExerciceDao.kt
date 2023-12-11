package com.app.muscu3000.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.app.muscu3000.model.Exercice

@Dao
interface ExerciceDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertExercice(exercice: Exercice) : Long

    @Query("SELECT * FROM Exercice WHERE exerciceId = :exerciceId")
    suspend fun getExerciceById(exerciceId: Long): Exercice?

    @Query("SELECT * FROM Exercice")
    suspend fun getAllExercises(): List<Exercice>

    @Query("SELECT Exercice.exerciceId, Exercice.exerciceName, Exercice.description FROM Exercice " +
            "JOIN GymSessionExercice on GymSessionExercice.exerciceId = Exercice.exerciceId" +
            " WHERE gymSessionId = :sessionId;")
    suspend fun getExercisesBySessionId(sessionId: Long): List<Exercice>
}
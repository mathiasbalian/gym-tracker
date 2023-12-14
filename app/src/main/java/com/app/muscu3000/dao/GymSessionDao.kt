package com.app.muscu3000.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.app.muscu3000.model.GymSession

@Dao
interface GymSessionDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGymSession(gymSession: GymSession) : Long

    @Query("SELECT * FROM GymSession WHERE gymSessionId = :gymSessionId")
    suspend fun getGymSessionById(gymSessionId: Long): GymSession?

    @Query("SELECT * FROM GymSession ORDER BY date DESC")
    suspend fun getAllGymSessions(): MutableList<GymSession>

    @Query("SELECT MAX(gymSessionId) + 1 FROM GymSession")
    suspend fun getNextSessionId(): Long

    @Query("DELETE FROM GymSession WHERE gymSessionId = :gymSessionId")
    suspend fun deleteGymSessionData(gymSessionId: Long)

    @Query("DELETE FROM UserGymSession WHERE gymSessionId = :gymSessionId")
    suspend fun deleteRelatedUserData(gymSessionId: Long)

    @Query("DELETE FROM GymSessionExercise WHERE gymSessionId = :gymSessionId")
    suspend fun deleteRelatedExerciseData(gymSessionId: Long)

    @Transaction
    suspend fun deleteGymSessionAndRelatedData(gymSessionId: Long)
    {
        deleteGymSessionData(gymSessionId)
        deleteRelatedUserData(gymSessionId)
        deleteRelatedExerciseData(gymSessionId)
    }

    @Update
    suspend fun updateGymSession(gymSession: GymSession)


}
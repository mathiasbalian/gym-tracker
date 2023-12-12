package com.app.muscu3000.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.app.muscu3000.model.GymSession

@Dao
interface GymSessionDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGymSession(gymSession: GymSession) : Long

    @Query("SELECT * FROM GymSession WHERE gymSessionId = :gymSessionId")
    suspend fun getGymSessionById(gymSessionId: Long): GymSession?

    @Query("SELECT * FROM GymSession")
    suspend fun getAllGymSessions(): MutableList<GymSession>


    @Query("DELETE FROM GymSession WHERE gymSessionId = :gymSessionId")
    abstract fun deleteGymSessionData(gymSessionId: Long)

    @Query("DELETE FROM UserGymSession WHERE gymSessionId = :gymSessionId")
    abstract fun deleteRelatedUserData(gymSessionId: Long)

    @Query("DELETE FROM GymSessionExercise WHERE gymSessionId = :gymSessionId")
    abstract fun deleteRelatedExerciceData(gymSessionId: Long)

    @Transaction
    fun deleteGymSessionAndRelatedData(gymSessionId: Long)
    {
        deleteGymSessionData(gymSessionId)
        deleteRelatedUserData(gymSessionId)
        deleteRelatedExerciceData(gymSessionId)
    }
}
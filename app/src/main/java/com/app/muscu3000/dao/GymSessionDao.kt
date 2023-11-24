package com.app.muscu3000.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.app.muscu3000.model.GymSession

@Dao
interface GymSessionDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGymSession(gymSession: GymSession)

    @Query("SELECT * FROM GymSession WHERE gymSessionId = :gymSessionId")
    suspend fun getGymSessionById(gymSessionId: Long): GymSession?
}
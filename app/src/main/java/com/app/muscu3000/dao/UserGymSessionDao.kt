package com.app.muscu3000.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.app.muscu3000.model.UserGymSession

@Dao
interface UserGymSessionDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUserGymSession(userGymSession: UserGymSession)
}
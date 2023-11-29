package com.app.muscu3000.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.app.muscu3000.model.GymSet

@Dao
interface GymSetDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSet(gymSet: GymSet)

    @Query("SELECT * FROM GymSet WHERE setId = :setId")
    suspend fun getSetById(setId: Long): GymSet?
}
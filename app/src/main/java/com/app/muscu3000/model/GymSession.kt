package com.app.muscu3000.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "GymSession")
data class GymSession(
    @PrimaryKey(autoGenerate = true)
    val gymSessionId: Long = 0,
    val date: String,
    val difficulty: String,
    val duration: Int
)

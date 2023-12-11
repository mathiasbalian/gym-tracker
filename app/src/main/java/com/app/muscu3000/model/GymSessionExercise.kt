package com.app.muscu3000.model

import androidx.room.Entity

@Entity(tableName = "GymSessionExercise", primaryKeys = ["gymSessionId", "exerciseId"])
data class GymSessionExercise(
    val gymSessionId: Long,
    val exerciseId: Long
)

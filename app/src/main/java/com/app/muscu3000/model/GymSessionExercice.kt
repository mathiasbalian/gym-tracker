package com.app.muscu3000.model

import androidx.room.Entity

@Entity(tableName = "GymSessionExercice", primaryKeys = ["gymSessionId", "exerciceId"])
data class GymSessionExercice(
    val gymSessionId: Long,
    val exerciceId: Long
)

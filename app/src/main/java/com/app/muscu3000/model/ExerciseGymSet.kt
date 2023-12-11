package com.app.muscu3000.model

import androidx.room.Entity

@Entity(tableName = "ExerciseGymSet", primaryKeys = ["exerciseId", "setId"])
data class ExerciseGymSet(
    val exerciseId: Long,
    val setId: Long
)

package com.app.muscu3000.model

import androidx.room.Entity

@Entity(tableName = "ExerciceGymSet", primaryKeys = ["exerciceId", "setId"])
data class ExerciceGymSet(
    val exerciceId: Long,
    val setId: Long
)

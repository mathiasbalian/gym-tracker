package com.app.muscu3000.model

import androidx.room.Entity

@Entity(tableName = "ExerciceSet", primaryKeys = ["exerciceId", "setId"])
data class ExerciceSet(
    val exerciceId: Long,
    val setId: Long
)

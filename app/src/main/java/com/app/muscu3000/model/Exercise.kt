package com.app.muscu3000.model

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "Exercise")
data class Exercise(
    @PrimaryKey(autoGenerate = true)
    val exerciseId: Long = 0,
    var exerciseName: String,
    val description: String?
)

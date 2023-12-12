package com.app.muscu3000.model

import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(tableName = "ExerciseGymSet",
    primaryKeys = ["exerciseId", "setId"],
    foreignKeys = [
        ForeignKey(
            entity = Exercise::class,
            parentColumns = ["exerciseId"],
            childColumns = ["exerciseId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = GymSet::class,
            parentColumns = ["setId"],
            childColumns = ["setId"],
            onDelete = ForeignKey.CASCADE
        )
    ])
data class ExerciseGymSet(
    val exerciseId: Long,
    val setId: Long
)

package com.app.muscu3000.model

import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(tableName = "ExerciseGymSet",
    primaryKeys = ["exerciseId", "setId"],
    foreignKeys = [
        ForeignKey(
            entity = Exercise::class,
            parentColumns = ["id"],
            childColumns = ["exerciseId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = GymSet::class,
            parentColumns = ["id"],
            childColumns = ["setId"],
            onDelete = ForeignKey.CASCADE
        )
    ])
data class ExerciseGymSet(
    val exerciseId: Long,
    val setId: Long
)

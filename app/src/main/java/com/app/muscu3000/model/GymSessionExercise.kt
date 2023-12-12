package com.app.muscu3000.model

import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(
    tableName = "GymSessionExercise",
    primaryKeys = ["gymSessionId", "exerciseId"],
    foreignKeys = [
        ForeignKey(
            entity = GymSession::class,
            parentColumns = ["gymSessionId"],
            childColumns = ["gymSessionId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = Exercise::class,
            parentColumns = ["exerciseId"],
            childColumns = ["exerciseId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)data class GymSessionExercise(
    val gymSessionId: Long,
    val exerciseId: Long
)

package com.app.muscu3000.model

import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(
    tableName = "UserGymSession",
    primaryKeys = ["userId", "gymSessionId"],
    foreignKeys = [
        ForeignKey(
            entity = User::class,
            parentColumns = ["id"],
            childColumns = ["userId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = GymSession::class,
            parentColumns = ["id"],
            childColumns = ["gymSessionId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)data class UserGymSession(
    val userId: Long,
    val gymSessionId: Long
)
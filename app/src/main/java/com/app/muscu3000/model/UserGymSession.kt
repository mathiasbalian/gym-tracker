package com.app.muscu3000.model

import androidx.room.Entity

@Entity(tableName = "UserGymSession", primaryKeys = ["userId", "gymSessionId"])
data class UserGymSession(
    val userId: Long,
    val gymSessionId: Long
)
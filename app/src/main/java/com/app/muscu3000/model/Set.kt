package com.app.muscu3000.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Set")
data class Set(
    @PrimaryKey(autoGenerate = true)
    val setId: Long = 0,
    val nbRep: Int,
    val weight: Double
)
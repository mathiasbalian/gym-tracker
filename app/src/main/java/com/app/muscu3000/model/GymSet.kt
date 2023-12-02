package com.app.muscu3000.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "GymSet")
data class GymSet(
    @PrimaryKey(autoGenerate = true)
    val setId: Long = 0,
    var nbRep: Int,
    var weight: Double,
    val setNumber : Int
)
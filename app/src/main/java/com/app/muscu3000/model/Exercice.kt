package com.app.muscu3000.model

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "Exercice")
data class Exercice(
    @PrimaryKey(autoGenerate = true)
    val exerciceId: Long = 0,
    val name: String,
    val description: String?
)

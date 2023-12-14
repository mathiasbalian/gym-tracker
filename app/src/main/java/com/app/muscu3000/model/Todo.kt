package com.app.muscu3000.model

data class Todo(
    val id: Int,
    val todo: String,
    var completed: Boolean,
    val userId: Int
)

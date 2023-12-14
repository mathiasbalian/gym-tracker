package com.app.muscu3000.model

data class TodoListResponse(
    val todos: List<Todo>,
    val total: Int,
    val skip: String,
    val limit: Int
)
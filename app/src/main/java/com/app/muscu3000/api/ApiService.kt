package com.app.muscu3000.api

import com.app.muscu3000.model.Todo
import com.app.muscu3000.model.TodoListResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
interface ApiService {
    @GET("todos?limit=10&skip=1")
    fun getTodos(): Call<TodoListResponse>

    @Headers("Content-Type: application/json")
    @POST("todos/add")
    fun postTodo(@Body todo: Todo): Call<Todo>

}
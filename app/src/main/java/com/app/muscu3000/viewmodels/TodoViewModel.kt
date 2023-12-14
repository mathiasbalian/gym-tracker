package com.app.muscu3000.viewmodels

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.app.muscu3000.api.ApiService
import com.app.muscu3000.model.Todo
import com.app.muscu3000.model.TodoListResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class TodoViewModel : ViewModel() {
    private var _todos: MutableLiveData<MutableList<Todo>> = MutableLiveData()
    private var _createdTodos: MutableLiveData<MutableList<Todo>> = MutableLiveData()
    private var _internetError: MutableLiveData<Boolean> = MutableLiveData(false)

    var internetError: LiveData<Boolean> = _internetError
    var todos: LiveData<MutableList<Todo>> = _todos
    var createdTodos: LiveData<MutableList<Todo>> = _createdTodos

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("https://dummyjson.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val apiService: ApiService = retrofit.create(ApiService::class.java)


    init {
        getTodos()
    }

    private fun getTodos(){
        val todoCall = apiService.getTodos()
        todoCall.enqueue(object: Callback<TodoListResponse> {
            override fun onResponse(call: Call<TodoListResponse>, response: Response<TodoListResponse>){
                if(response.isSuccessful) {
                    _todos.value = response.body()?.todos as MutableList<Todo>
                    todos = _todos
                }
            }

            override fun onFailure(call: Call<TodoListResponse>, t: Throwable) {
                println(t)
            }
        })
    }

}
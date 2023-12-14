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
    private var _internetError: MutableLiveData<Boolean> = MutableLiveData(false)

    var internetError: LiveData<Boolean> = _internetError
    var todos: MutableLiveData<MutableList<Todo>> = _todos

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("https://dummyjson.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val apiService: ApiService = retrofit.create(ApiService::class.java)

    init {
        System.out.println("AAZFZEFZE")
        getTodos()
        System.out.println(this._todos.value)
    }

    fun getTodos(){
        System.out.println("PPPPPPPPPPPPPPPPPPPPPPP")
        val todoCall = apiService.getTodos()
        todoCall.enqueue(object: Callback<TodoListResponse> {
            override fun onResponse(call: Call<TodoListResponse>, response: Response<TodoListResponse>){
                if(response.isSuccessful) {
                    System.out.println("success")
                    _todos.value = response.body()?.todos as MutableList<Todo>
                    todos = _todos
                    _internetError.value = false
                    internetError = _internetError
                }
                else {
                    System.out.println("FAIL")
                    _internetError.value = true
                    internetError = _internetError
                }
            }

            override fun onFailure(call: Call<TodoListResponse>, t: Throwable) {
                println(t)
                _internetError.value = true
                internetError = _internetError
            }
        })
    }

    fun createTodo(todoContent: String, todoIsCompleted: Boolean){
        val todoCall = apiService.postTodo(Todo(id = 1, todo = todoContent, completed = todoIsCompleted, userId = 1))
        todoCall.enqueue(object: Callback<Todo> {
            override fun onResponse(call: Call<Todo>, response: Response<Todo>) {
                if(response.isSuccessful){
                    _todos.value?.add(response.body()!!)
                    todos.value = _todos.value
                    _internetError.value = false
                    internetError = _internetError
                }
                else {
                    _internetError.value = true
                    internetError = _internetError
                }
            }

            override fun onFailure(call: Call<Todo>, t: Throwable) {
                println(t)
                _internetError.value = true
                internetError = _internetError
            }

        })
    }

}
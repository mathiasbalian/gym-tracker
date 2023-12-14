package com.app.muscu3000.fragments

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.muscu3000.Adapter.HomeGymSessionAdapter
import com.app.muscu3000.Adapter.TodoAdapter
import com.app.muscu3000.R
import com.app.muscu3000.viewmodels.TodoViewModel
import com.google.android.material.textfield.TextInputEditText

class InternetFragment : Fragment(R.layout.internet_fragment) {

    private val todoViewModel: TodoViewModel by activityViewModels()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val todoRecyclerView: RecyclerView = view.findViewById(R.id.todoRecyclerView)
        val todoContentCreate: TextInputEditText = view.findViewById(R.id.todoContentCreate)
        val completedCheckBoxCreate: CheckBox = view.findViewById(R.id.completedCheckBoxCreate)
        val createTodoButton: Button = view.findViewById(R.id.createTodoBtn)
        val errorTxt: TextView = view.findViewById(R.id.errorTextView)

        createTodoButton.setOnClickListener {
            todoViewModel.createTodo(todoContentCreate.text.toString(), completedCheckBoxCreate.isChecked)
        }

        todoViewModel.internetError.observe(viewLifecycleOwner, Observer {
            if(it){
                errorTxt.visibility = View.VISIBLE
                todoViewModel.getTodos()
            }
            else {
                errorTxt.visibility = View.GONE
            }
        })

        todoRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        todoViewModel.todos.observe(viewLifecycleOwner, Observer {
            for(todo in todoViewModel.todos.value!!){
                println(todo.todo)
            }
            todoRecyclerView.adapter = todoViewModel.todos.value?.let { it1 -> TodoAdapter(it1) }
        })

    }
}
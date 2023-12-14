package com.app.muscu3000.fragments

import android.os.Bundle
import android.view.View
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

class InternetFragment : Fragment(R.layout.internet_fragment) {

    private val todoViewModel: TodoViewModel by activityViewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val todoRecyclerView: RecyclerView = view.findViewById(R.id.todoRecyclerView)

        todoRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        todoViewModel.todos.observe(viewLifecycleOwner, Observer {
            for(todo in todoViewModel.todos.value!!){
                println(todo.todo)
            }
            todoRecyclerView.adapter = todoViewModel.todos.value?.let { it1 -> TodoAdapter(it1) }
        })

    }
}
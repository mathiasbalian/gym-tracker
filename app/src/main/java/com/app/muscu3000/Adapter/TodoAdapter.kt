package com.app.muscu3000.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.app.muscu3000.R
import com.app.muscu3000.model.Todo

class TodoAdapter(private val todos: MutableList<Todo>): RecyclerView.Adapter<TodoAdapter.TodoViewHolder>() {

    class TodoViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val todoContent: TextView
        private val todoCompleted: CheckBox

        init {
            todoContent = view.findViewById(R.id.todoContentTxt)
            todoCompleted = view.findViewById(R.id.completedCheckBox)
        }

        fun onBind(todo: Todo, update: () -> Unit) {
            todoContent.text = todo.todo
            todoCompleted.isChecked = todo.completed
            todoCompleted.setOnCheckedChangeListener { _, isChecked ->
                todo.completed = isChecked
                update()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.todo_holder, parent, false)

        return TodoViewHolder(view)
    }
    override fun getItemCount(): Int = todos.size
    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        holder.onBind(todos[position]) {
            holder.itemView.post {
                notifyItemChanged(position)
            }
        }
    }
}

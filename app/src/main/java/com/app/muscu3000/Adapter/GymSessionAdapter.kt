package com.app.muscu3000.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import com.app.muscu3000.R
import com.app.muscu3000.model.ExerciceInfos
import com.app.muscu3000.model.GymSet
import com.google.android.material.textfield.TextInputEditText

class GymSessionAdapter(private val exerciceList: MutableList<ExerciceInfos>) :
    RecyclerView.Adapter<GymSessionAdapter.ViewHolder>() {
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view)  {
        private val exerciceName: TextInputEditText

        val layout: LinearLayout = view.findViewById(R.id.elementInnerRecyclerView)

        init {
            exerciceName = view.findViewById(R.id.exerciseNameEditText)

            // Add touch listener to close the keyboard when touched outside the TextInputEditText
            itemView.setOnTouchListener { _, event ->
                if (event.action == MotionEvent.ACTION_DOWN) {
                    hideKeyboard(itemView.context, itemView)
                }
                false
            }
        }

        fun onBind(data: ExerciceInfos, update: () -> Unit) {
            exerciceName.setText(data.exercice.name)
        }

        private fun hideKeyboard(context: Context, view: View) {
            val inputMethodManager =
                context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.exercise_holder, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentExercise = exerciceList[position]

        for (i in holder.layout.childCount until currentExercise.listGymSet.count()) {
            val inflater = LayoutInflater.from(holder.itemView.context)
            val linearLayout = inflater.inflate(R.layout.set_holder, null) as LinearLayout
            holder.layout.addView(linearLayout, holder.layout.childCount-1)
        }

        // Set up the "Add Set" button click listener
        val addSetButton = holder.itemView.findViewById<Button>(R.id.addSetButton)
        addSetButton.setOnClickListener {
            // Generate a new GymSet and add it to the adapter
            val newSet = GymSet(nbRep = 0, weight = 0.0)
            addSet(newSet, position)
            notifyDataSetChanged()
        }
    }

    override fun getItemCount(): Int {
        return exerciceList.size
    }

    fun addExercice(exercice: ExerciceInfos) {
        exerciceList.add(exercice)
        notifyDataSetChanged()
    }

    fun addSet(gymSet: GymSet, position: Int) {
        exerciceList[position].listGymSet.add(gymSet)
        notifyDataSetChanged()
    }
}

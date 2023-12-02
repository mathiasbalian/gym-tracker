package com.app.muscu3000.Adapter

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.app.muscu3000.R
import com.app.muscu3000.model.ExerciceInfos
import com.app.muscu3000.model.GymSet
import com.google.android.material.textfield.TextInputEditText

class GymSessionAdapter(private val exerciceList: MutableList<ExerciceInfos>) :
    RecyclerView.Adapter<GymSessionAdapter.ViewHolder>() {
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view)  {
        val exerciceName: TextInputEditText
        val layout: LinearLayout

        init {
            exerciceName = view.findViewById(R.id.exerciseNameEditText)
            layout = view.findViewById<LinearLayout>(R.id.elementInnerRecyclerView)

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

        holder.exerciceName.setText(currentExercise.exercice.name)

        holder.exerciceName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                val nameValue = s.toString()
                currentExercise.exercice.name = nameValue
            }
        })

        for (i in holder.layout.childCount until currentExercise.listGymSet.count()) {
            val inflater = LayoutInflater.from(holder.itemView.context)
            val linearLayout = inflater.inflate(R.layout.set_holder, null) as LinearLayout
            holder.layout.addView(linearLayout, holder.layout.childCount - 1)


            linearLayout.findViewById<TextView>(R.id.setNumberTextView).text = "Set ${currentExercise.listGymSet[i].setNumber.toString()}"

            val nbRepsEditText = linearLayout.findViewById<TextInputEditText>(R.id.nbRepsEditText)
            val nbRepsValue = currentExercise.listGymSet[i].nbRep

            nbRepsEditText.setText(if (nbRepsValue == 0) "" else nbRepsValue.toString())

            val weightEditText = linearLayout.findViewById<TextInputEditText>(R.id.weightEditText)
            val weightValue = currentExercise.listGymSet[i].weight

            weightEditText.setText(if (weightValue == 0.0) "" else weightValue.toString())

        }

        // Set up TextWatcher listeners for nbRepsEditText and weightEditText
        for (i in 0 until holder.layout.childCount) {
            val childView = holder.layout.getChildAt(i)

            if (childView is LinearLayout) {
                val linearLayout = childView

                val nbRepsEditText = linearLayout.findViewById<TextInputEditText>(R.id.nbRepsEditText)
                nbRepsEditText.addTextChangedListener(object : TextWatcher {
                    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

                    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

                    override fun afterTextChanged(s: Editable?) {
                        val nbRepsValue = s.toString().toIntOrNull() ?: 0
                        currentExercise.listGymSet[i].nbRep = nbRepsValue
                    }
                })

                val weightEditText = linearLayout.findViewById<TextInputEditText>(R.id.weightEditText)
                weightEditText.addTextChangedListener(object : TextWatcher {
                    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

                    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

                    override fun afterTextChanged(s: Editable?) {
                        val weightValue = s.toString().toDoubleOrNull() ?: 0.0
                        currentExercise.listGymSet[i].weight = weightValue
                    }
                })
            }
        }

        // Set up the "Add Set" button click listener
        val addSetButton = holder.itemView.findViewById<Button>(R.id.addSetButton)
        addSetButton.setOnClickListener {
            // Generate a new GymSet and add it to the adapter
            val newSet = GymSet(nbRep = 0, weight = 0.0, setNumber = holder.layout.childCount)
            addSet(newSet, position)
            notifyDataSetChanged()
        }

//        // Set up TextWatcher listeners
//        holder.setListeners(currentExercise) {
//            notifyDataSetChanged()
//        }
    }


    override fun getItemCount(): Int {
        return exerciceList.size
    }

    fun getExerciceInfo() : MutableList<ExerciceInfos> {
        return exerciceList
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
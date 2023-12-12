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
import com.app.muscu3000.model.ExerciseInfos
import com.app.muscu3000.model.GymSet
import com.google.android.material.textfield.TextInputEditText

class GymSessionAdapter(private val exerciseList: MutableList<ExerciseInfos>) :
    RecyclerView.Adapter<GymSessionAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view)  {
        val exerciseName: TextInputEditText
        val layout: LinearLayout
        val addSetButton: Button

        init {
            exerciseName = view.findViewById(R.id.exerciseNameEditText)
            layout = view.findViewById<LinearLayout>(R.id.elementInnerRecyclerView)
            addSetButton = view.findViewById(R.id.addSetButton)

            // Add touch listener to close the keyboard when touched outside the TextInputEditText
            itemView.setOnTouchListener { _, event ->
                if (event.action == MotionEvent.ACTION_DOWN) {
                    hideKeyboard(itemView.context, itemView)
                }
                false
            }
        }

        fun setupTextWatchersAndListeners(data: ExerciseInfos) {

            exerciseName.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

                override fun afterTextChanged(s: Editable?) {
                    val nameValue = s.toString()
                    data.exercise.exerciseName = nameValue
                }
            })

            // Set up TextWatcher listeners for nbRepsEditText and weightEditText
            for (i in 0 until layout.childCount) {
                val childView = layout.getChildAt(i)

                if (childView is LinearLayout) {
                    val linearLayout = childView

                    val nbRepsEditText =
                        linearLayout.findViewById<TextInputEditText>(R.id.nbRepsEditText)
                    nbRepsEditText.addTextChangedListener(object : TextWatcher {
                        override fun beforeTextChanged(
                            s: CharSequence?,
                            start: Int,
                            count: Int,
                            after: Int
                        ) {}

                        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

                        override fun afterTextChanged(s: Editable?) {
                            val nbRepsValue = s.toString().toIntOrNull() ?: 0
                            System.out.println("change detected : " + nbRepsValue)
                            data.listGymSet[i].nbRep = nbRepsValue
                        }
                    })

                    val weightEditText =
                        linearLayout.findViewById<TextInputEditText>(R.id.weightEditText)
                    weightEditText.addTextChangedListener(object : TextWatcher {
                        override fun beforeTextChanged(
                            s: CharSequence?,
                            start: Int,
                            count: Int,
                            after: Int
                        ) {}

                        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

                        override fun afterTextChanged(s: Editable?) {
                            val weightValue = s.toString().toDoubleOrNull() ?: 0.0
                            data.listGymSet[i].weight = weightValue
                        }
                    })
                }
            }

            // Set up the "Add Set" button click listener
            addSetButton.setOnClickListener {
                // Generate a new GymSet and add it to the adapter
                val newSet = GymSet(nbRep = 0, weight = 0.0, setNumber = layout.childCount)
                addSet(newSet, adapterPosition)
            }
        }

        fun onBind(data: ExerciseInfos, update: () -> Unit) {
            exerciseName.setText(data.exercise.exerciseName)

            for (gymSet in data.listGymSet) {
                System.out.println("Set number : " + gymSet.setNumber)
                System.out.println(gymSet.nbRep)
                System.out.println(gymSet.weight)

                val inflater = LayoutInflater.from(itemView.context)
                val linearLayout = inflater.inflate(R.layout.set_holder, null) as LinearLayout

                val nbRepsEditText = linearLayout.findViewById<TextInputEditText>(R.id.nbRepsEditText)
                val nbRepsValue = gymSet.nbRep

                System.out.println("ok" + nbRepsEditText.text.toString())

                if (nbRepsEditText.text.toString() != nbRepsValue.toString()) {
                    nbRepsEditText.setText(if (nbRepsValue == 0) "" else nbRepsValue.toString())
                }
                val weightEditText = linearLayout.findViewById<TextInputEditText>(R.id.weightEditText)
                val weightValue = gymSet.weight

                if (weightEditText.text.toString() != weightValue.toString()) {
                    weightEditText.setText(if (nbRepsValue == 0) "" else nbRepsValue.toString())
                }
            }

            for (i in layout.childCount - 1 until data.listGymSet.count()) {
                val inflater = LayoutInflater.from(itemView.context)
                val linearLayout = inflater.inflate(R.layout.set_holder, null) as LinearLayout
                layout.addView(linearLayout, layout.childCount - 1)

                linearLayout.findViewById<TextView>(R.id.setNumberTextView).text =
                    "Set ${data.listGymSet[i].setNumber.toString()}"

                val nbRepsEditText = linearLayout.findViewById<TextInputEditText>(R.id.nbRepsEditText)
                val nbRepsValue = data.listGymSet[i].nbRep
                nbRepsEditText.setText(if (nbRepsValue == 0) "" else nbRepsValue.toString())

                val weightEditText = linearLayout.findViewById<TextInputEditText>(R.id.weightEditText)
                val weightValue = data.listGymSet[i].weight
                weightEditText.setText(if (weightValue == 0.0) "" else weightValue.toString())
            }

            setupTextWatchersAndListeners(data)
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
        val currentExercise = exerciseList[position]
        holder.onBind(currentExercise) {
            notifyDataSetChanged()
        }
    }

    override fun getItemCount(): Int {
        return exerciseList.size
    }

    fun getExerciseInfo() : MutableList<ExerciseInfos> {
        return exerciseList
    }

    fun addExercise(exercise: ExerciseInfos) {
        exerciseList.add(exercise)
        notifyDataSetChanged()
    }

    fun addSet(gymSet: GymSet, position: Int) {
        exerciseList[position].listGymSet.add(gymSet)
        notifyItemChanged(position)
    }
}

package com.app.muscu3000.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.RecyclerView
import com.app.muscu3000.R
import com.app.muscu3000.model.GymSession
import com.app.muscu3000.viewmodels.GymSessionsViewModel

class HomeGymSessionAdapter(private val sessionList: MutableList<GymSession>,
    private val gymSessionsViewModel: GymSessionsViewModel):
    RecyclerView.Adapter<HomeGymSessionAdapter.GymSessionHolder>() {

        inner class GymSessionHolder(view: View) : RecyclerView.ViewHolder(view){
            private val sessionDetails: TextView
            private val editImageView: ImageView
            private val expandImageView: ImageView
            private val isExpanded: Boolean

            init {
                sessionDetails = view.findViewById(R.id.sessionDetailsTxt)
                editImageView = view.findViewById(R.id.editIv)
                expandImageView = view.findViewById(R.id.expandIv)
                isExpanded = false
            }

            fun onBind(gymSession: GymSession){
                sessionDetails.text = "${gymSession.name} ${gymSession.date}"

                editImageView.setOnClickListener {
                    gymSessionsViewModel.setSelectedSession(gymSession)
                    // TODO: INSERT NAVIGATION TO EDIT SESSION
                }

                expandImageView.setOnClickListener {

                }
            }
        }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GymSessionHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.gym_session_holder, parent, false)
        return GymSessionHolder(view)
    }

    override fun getItemCount(): Int = sessionList.size

    override fun onBindViewHolder(holder: GymSessionHolder, position: Int) {
        holder.onBind(sessionList[position])
    }
}
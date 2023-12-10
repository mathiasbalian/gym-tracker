package com.app.muscu3000.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.muscu3000.Adapter.GymSessionAdapter
import com.app.muscu3000.Adapter.HomeGymSessionAdapter
import com.app.muscu3000.MainActivity
import com.app.muscu3000.R
import com.app.muscu3000.model.GymSession
import com.app.muscu3000.viewmodels.GymSessionsViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeFragment : Fragment(R.layout.home_fragment) {

    private val gymSessionViewModel: GymSessionsViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val gymSessionRecyclerView: RecyclerView = view.findViewById(R.id.gymSessionRecyclerView)
        gymSessionRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        gymSessionViewModel.sessionList.observe(viewLifecycleOwner, Observer {
            gymSessionRecyclerView.adapter = HomeGymSessionAdapter(it, gymSessionViewModel)
        })

    }
}
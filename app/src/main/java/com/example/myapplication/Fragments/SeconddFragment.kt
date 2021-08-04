package com.example.myapplication.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.Adaptor.profileAdaptor
import com.example.myapplication.R

class SeconddFragment : Fragment() {
lateinit var recyclerview:RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var v=  inflater.inflate(R.layout.fragment_secondd, container, false)




        recyclerview=v.findViewById(R.id.recyclervieww)
        var adaptor = profileAdaptor()
        val layoutManager = LinearLayoutManager(activity)
        recyclerview.layoutManager = layoutManager
        recyclerview.adapter = adaptor
        return  v
    }

}
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

class FirstFragment : Fragment() {

lateinit var recycler:RecyclerView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
         var v=  inflater.inflate(R.layout.fragment_first, container, false)




        recycler=v.findViewById(R.id.recycler_view_tab1)
        var adaptor = profileAdaptor()
        val layoutManager = LinearLayoutManager(activity)
        recycler.layoutManager = layoutManager
        recycler.adapter = adaptor

        return v

    }


}
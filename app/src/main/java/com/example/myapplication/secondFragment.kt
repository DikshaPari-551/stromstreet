package com.example.myapplication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView


class secondFragment : Fragment() {
lateinit var man:ImageView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var v= inflater.inflate(R.layout.fragment_second, container, false)
        man=v.findViewById(R.id.user)
        man.setOnClickListener{
            getFragmentManager()?.beginTransaction()?.replace(R.id.linear_layout, ProfileFragment())
                ?.commit()
        }
        return v
    }

}
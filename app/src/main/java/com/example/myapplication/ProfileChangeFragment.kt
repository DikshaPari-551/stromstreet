package com.example.myapplication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView


class ProfileChangeFragment : Fragment() {
    lateinit var cameraProfileimg:ImageView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var v= inflater.inflate(R.layout.fragment_profile_change, container, false)
        cameraProfileimg=v.findViewById(R.id.img_camera_profile)
        cameraProfileimg.setOnClickListener{
            var bottomsheet=bottomSheetDialog()
            fragmentManager?.let { it1 -> bottomsheet.show(it1,"bottomsheet") }
        }

        return v
    }


}
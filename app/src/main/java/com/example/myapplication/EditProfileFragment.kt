package com.example.myapplication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout


class EditProfileFragment : Fragment() {
lateinit var layoutButtonProfileDetail:LinearLayout
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var v = inflater.inflate(R.layout.fragment_edit_profile, container, false)
    layoutButtonProfileDetail=v.findViewById(R.id.layout_button_profile_details)
        layoutButtonProfileDetail.setOnClickListener{
            getFragmentManager()?.beginTransaction()?.replace(R.id.linear_layout, ProfileChangeFragment())
                ?.commit()
        }
    return v
    }

}
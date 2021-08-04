package com.example.myapplication.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import com.example.myapplication.*
import com.example.myapplication.BottomSheets.BottomSheetLogout


class EditProfileFragment : Fragment() {
lateinit var layoutButtonProfileDetail:LinearLayout
    lateinit var logout:RelativeLayout
    lateinit var man_EditProfile:ImageView
    lateinit var filter_EditProfile:ImageView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var v = inflater.inflate(R.layout.fragment_edit_profile, container, false)
    layoutButtonProfileDetail=v.findViewById(R.id.layout_button_profile_details)
        logout=v.findViewById(R.id.layout_logout_button)
        logout.setOnClickListener{
            var bottomsheettt=
                BottomSheetLogout()
            fragmentManager?.let { it1 -> bottomsheettt.show(it1,"bottomsheet") }

        }
        man_EditProfile=v.findViewById(R.id.man_editprofile)
        man_EditProfile.setOnClickListener{
            getFragmentManager()?.beginTransaction()?.replace(
                R.id.linear_layout,
                ProfileFragment()
            )
                ?.commit()
        }
        filter_EditProfile=v.findViewById(R.id.filter_editprofile)
        filter_EditProfile.setOnClickListener{
            getFragmentManager()?.beginTransaction()?.replace(
                R.id.linear_layout,
                secondFragment()
            )
                ?.commit()
        }

//        logout.setOnClickListener{
//            var bottomSheetLogout=BottomSheetLogout()
//            fragmentManager?.let { it1 -> bottomSheetLogout.show(it1, "bottomsheet") }
//
//        }
        layoutButtonProfileDetail.setOnClickListener{
            getFragmentManager()?.beginTransaction()?.replace(
                R.id.linear_layout,
                ProfileChangeFragment()
            )
                ?.commit()
        }
    return v
    }

}
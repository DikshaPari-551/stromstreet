package com.example.myapplication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import androidx.fragment.app.FragmentManager
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout


class ProfileFragment : Fragment() {
    lateinit var tag:ImageView
    lateinit var back_tab1:LinearLayout
    lateinit var manProfile:ImageView
    lateinit var filterProfile:ImageView

lateinit var color_grid:ImageView
    lateinit var back_tab:LinearLayout
    lateinit var layout_tab1:RelativeLayout
    lateinit var layout_tab2:RelativeLayout
    lateinit var buttonProfileDetail:LinearLayout
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var v = inflater.inflate(R.layout.fragment_profile, container, false)
        tag=v.findViewById(R.id.tag)
        back_tab1=v.findViewById(R.id.back_tab1)
        buttonProfileDetail=v.findViewById(R.id.button_profile_detail)
        buttonProfileDetail.setOnClickListener{
            getFragmentManager()?.beginTransaction()?.replace(R.id.linear_layout, EditProfileFragment())
                ?.commit()

        }
        manProfile=v.findViewById(R.id.man_profile)
        manProfile.setOnClickListener{
            getFragmentManager()?.beginTransaction()?.replace(R.id.linear_layout, ProfileFragment())
                ?.commit()
        }
        filterProfile=v.findViewById(R.id.filter_profile)
        filterProfile.setOnClickListener{
            getFragmentManager()?.beginTransaction()?.replace(R.id.linear_layout, secondFragment())
                ?.commit()

        }

        color_grid=v.findViewById(R.id.color_grid)
        back_tab=v.findViewById(R.id.back_tab)
        layout_tab1=v.findViewById(R.id.layout_tab1)
        layout_tab2=v.findViewById(R.id.layout_tab2)

        color_grid.setImageDrawable(resources.getDrawable(R.drawable.color_grid))
        back_tab.setBackgroundResource(R.drawable.rectangle_tab)
        tag.setImageDrawable(resources.getDrawable(R.drawable.tag))
        back_tab1.setBackgroundColor(resources.getColor(R.color.edit_color))
        getFragmentManager()?.beginTransaction()?.replace(R.id.layout_tabsss, FirstFragment())
            ?.commit()

        layout_tab1.setOnClickListener{
            color_grid.setImageDrawable(resources.getDrawable(R.drawable.color_grid))
            back_tab.setBackgroundResource(R.drawable.rectangle_tab)
            tag.setImageDrawable(resources.getDrawable(R.drawable.tag))
            back_tab1.setBackgroundColor(resources.getColor(R.color.edit_color))
            getFragmentManager()?.beginTransaction()?.replace(R.id.layout_tabsss, FirstFragment())
                ?.commit()

        }
        layout_tab2.setOnClickListener{
            color_grid.setImageDrawable(resources.getDrawable(R.drawable.grid))
            back_tab.setBackgroundColor(resources.getColor(R.color.edit_color))
            tag.setImageDrawable(resources.getDrawable(R.drawable.ggg))
            back_tab1.setBackgroundResource(R.drawable.rectangle_tab)
            getFragmentManager()?.beginTransaction()?.replace(R.id.layout_tabsss, SeconddFragment())
                ?.commit()

        }
//        tab_layout=v.findViewById(R.id.tab_layout)
//        viewPager=v.findViewById(R.id.viewPager)

//        var mFragAdaptor= fragmentManager?.let { FragAdaptor(it) }
//        viewPager.adapter=mFragAdaptor
//        tab_layout.setupWithViewPager(viewPager)
//        mFragAdaptor?.add(FirstFragment(),"one")
//        mFragAdaptor?.add(SeconddFragment(),"two")

        return v
    }


}
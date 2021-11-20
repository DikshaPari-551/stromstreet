package com.example.myapplication.Adaptor

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.myapplication.Fragments.FirstFragment
import com.example.myapplication.Fragments.SeconddFragment

class FragAdaptor(fm: FragmentManager) : FragmentPagerAdapter(fm) {
    private var fragmentList: ArrayList<Fragment> = ArrayList()
    private var titleList: ArrayList<String> = ArrayList()

    fun add(fragment: Fragment, title: String) {
        titleList.add(title)
        fragmentList.add(fragment)
    }

    override fun getItem(position: Int): Fragment {

        when(position){
            0->{return FirstFragment()
            }
            1->{ return SeconddFragment()
            }
          else->{return FirstFragment()
          }
        }
    }

    override fun getCount(): Int {
        return 2
    }


    override fun getPageTitle(position: Int): CharSequence? {
       when(position){
           0->{return "one"}
           1->{return  "two"}
       }
        c
        return super.getPageTitle(position)
    }

}
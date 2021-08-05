package com.example.myapplication.Adaptor

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.myapplication.Fragments.GalleryFragment
import java.util.*
import kotlin.collections.ArrayList

//class GalleryPostViewAdapter(
//    owner: FragmentActivity, frag1: AddPostFragment.Companion, frag2: GalleryFragment.Companion
//) :
//    FragmentStateAdapter(owner) {
//    private var fragments = ArrayList<Fragment>()
//
//
//
//    override fun getItemCount(): Int {
//        return fragments.size
//    }
//
//    override fun createFragment(position: Int): Fragment {
//        return fragments[position]
//    }
//}

//
class GalleryPostViewAdapter(owner : FragmentActivity ,frags : ArrayList<Fragment>) :
    FragmentStateAdapter(owner) {
    private val fragments = java.util.ArrayList<Fragment>()
    override fun createFragment(position: Int): Fragment {
        return fragments[position]
    }

    override fun getItemCount(): Int {
        return fragments.size
    }

    init {
        this.fragments.addAll(frags)
    }
}

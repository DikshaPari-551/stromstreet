package com.example.myapplication.Activities

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.myapplication.Adaptor.GalleryPostViewAdapter
import com.example.myapplication.Fragments.AddPostFragment
import com.example.myapplication.Fragments.GalleryFragment
import com.example.myapplication.R
import com.example.myapplication.bottomSheetDialog

class GalleryPostActivity : AppCompatActivity() {
    private val TAG = "GalleryPostActivity"

//    private var galleryPostActivity: GalleryPostActivity? = null

    private var pager: ViewPager2? = null

    private var addPostFragment: AddPostFragment? = null
    private var galleryFragment: GalleryFragment? = null
    private var bottomSheetDialog: bottomSheetDialog? = null


    companion object {
        var galleryPostActivity = GalleryPostActivity()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gallery_post)
        pager = findViewById(R.id.pager)
        addPostFragment = AddPostFragment()
        galleryFragment = GalleryFragment(addPostFragment!!)

        init()
    }

    private fun init() {
        val args = Bundle()
        addPostFragment = AddPostFragment()
        addPostFragment!!.setArguments(args)
        galleryFragment = GalleryFragment(addPostFragment!!)
        galleryFragment!!.setArguments(args)
        var list : ArrayList<Fragment> = arrayListOf()
        list.add(addPostFragment!!)
        list.add(galleryFragment!!)
        pager!!.adapter = GalleryPostViewAdapter(this,list)
        pager!!.isUserInputEnabled = false

    }

    fun toggleGalleryFragment(open: Boolean) {
        galleryPostActivity.pager?.setCurrentItem(if (open) 1 else 0)
        val data: List<String> = galleryPostActivity.addPostFragment!!.getSelectedData()
        Log.d(TAG, "toggleGalleryFragment: data = $data")
        galleryPostActivity.galleryFragment!!.setSelectedData(data)
    }

    override fun onResume() {
        super.onResume()
        galleryPostActivity = this
    }

    override fun onDestroy() {
        super.onDestroy()
//        galleryPostActivity = null
    }
}
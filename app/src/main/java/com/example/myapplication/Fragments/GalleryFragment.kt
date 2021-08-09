package com.example.myapplication.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.myapplication.R
import com.github.chiragji.gallerykit.GalleryKitView
import com.github.chiragji.gallerykit.callbacks.GalleryKitListener

class GalleryFragment(private val listener: GalleryKitListener) : Fragment() {
    private var galleryKitView: GalleryKitView? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_gallery, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        galleryKitView = view.findViewById(R.id.galleryKitView)
        galleryKitView!!.attach(this)
        galleryKitView!!.registerGalleryKitListener(listener)
    }

    fun setSelectedData(dataList: List<String?>) {
        if (galleryKitView != null) galleryKitView!!.selectedData = dataList
    }
}
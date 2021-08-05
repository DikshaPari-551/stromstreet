package com.example.myapplication.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.Activities.GalleryPostActivity.Companion.galleryPostActivity
import com.example.myapplication.Adaptor.ItemListAdapter
import com.example.myapplication.R
import com.github.chiragji.gallerykit.GalleryKitDialog
import com.github.chiragji.gallerykit.callbacks.GalleryKitListener
import com.github.chiragji.gallerykit.enums.GalleryKitViewStyle
import com.google.android.material.button.MaterialButton


class AddPostFragment : Fragment(), GalleryKitListener {

    private lateinit var selectedItemsListView: RecyclerView


    private var adapter: ItemListAdapter? = null

//    private var kitDialog: GalleryKitDialog? = null
//    lateinit var gallery: TextView


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_post, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        gallery = view.findViewById(R.id.gallery_open)
        selectedItemsListView = view.findViewById(R.id.selectedItemsListView)
        init()
    }

    private fun init() {
//
//
//        gallery!!.setOnClickListener { view: View? ->
//            galleryPostActivity.toggleGalleryFragment(
//                true
//            )
//        }
        selectedItemsListView!!.layoutManager = LinearLayoutManager(requireContext())
        adapter = ItemListAdapter()
        selectedItemsListView!!.setAdapter(adapter)
//
    }

//    private fun toggleListView() {
//        if (adapter?.getItemCount() === 0) {
//            noSelectionView!!.visibility = View.VISIBLE
//            selectedItemsListView!!.visibility = View.GONE
//        } else {
//            noSelectionView!!.visibility = View.GONE
//            selectedItemsListView!!.visibility = View.VISIBLE
//        }
//    }

    override fun onGalleryKitBackAction() {
        galleryPostActivity.toggleGalleryFragment(false)    }

    override fun onGalleryKitSelectionConfirmed(selectedDataUris: MutableList<String>) {
        adapter?.setAllData(selectedDataUris)
        galleryPostActivity.toggleGalleryFragment(false)
//        toggleListView()
    }

    fun getSelectedData(): List<String> {
        return adapter?.getSelectedDataList()!!
    }
}
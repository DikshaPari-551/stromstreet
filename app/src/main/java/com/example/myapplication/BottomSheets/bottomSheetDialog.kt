package com.example.myapplication

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.Activities.GalleryPostActivity
import com.example.myapplication.Adaptor.ItemListAdapter
import com.example.myapplication.Fragments.AddPostFragment
import com.github.chiragji.gallerykit.GalleryKitDialog
import com.github.chiragji.gallerykit.callbacks.GalleryKitListener
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.io.*


class bottomSheetDialog : BottomSheetDialogFragment() {
    var pic_id = 123
    private val pickImage = 100
    lateinit var cancel: TextView
    lateinit var gallery: TextView
    lateinit var camera: TextView

    private val selectedItemsListView: RecyclerView? = null
    private val noSelectionView: TextView? = null

    private var adapter: ItemListAdapter? = null

    private val kitDialog: GalleryKitDialog? = null
    var galleryPostActivity : GalleryPostActivity? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var v = inflater.inflate(R.layout.bottom_drawer, container, false)

        galleryPostActivity = GalleryPostActivity()
        gallery = v.findViewById(R.id.gallery_open)
        camera = v.findViewById(R.id.camera_open)
        cancel = v.findViewById(R.id.cancel)
//        var bottomsheet=bottomSheetDialog()

        cancel.setOnClickListener {
            dismiss()

        }
//            val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
//            startActivityForResult(gallery, pickImage)
//            fragmentManager?.beginTransaction()?.replace(R.id.layout, AddPostFragment())?.commit()
//            dismiss()
//            val i = Intent(activity, GalleryPostActivity::class.java)
//            startActivity(i)


                gallery.setOnClickListener { view: View? ->
//                    GalleryPostActivity.galleryPostActivity.toggleGalleryFragment(
//                        true
//                    )
//                    var i = Intent(activity, GalleryPostActivity()::class.java)
//                    startActivity(i)

                }


//            }(View.OnClickListener { view: View? ->
//                galleryPostActivity?.toggleGalleryFragment(
//                    true
//                )
//            })
//
//            selectedItemsListView?.setLayoutManager(LinearLayoutManager(requireContext()))
//            adapter = ItemListAdapter()
//            selectedItemsListView?.setAdapter(adapter)


        camera.setOnClickListener {
//            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
//            val f = File(Environment.getExternalStorageDirectory(), "temp.jpg")
//            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f))
//            startActivityForResult(intent, 1)
            fragmentManager?.beginTransaction()?.replace(R.id.linear_layout, AddPostFragment())?.commit()
            dismiss()

        }
        return v
    }


    override fun getTheme(): Int = R.style.BottomSheetDialogTheme
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog =
        BottomSheetDialog(requireContext(), theme)
//
//    override fun onGalleryKitBackAction() {
//        TODO("Not yet implemented")
//    }
//
//    override fun onGalleryKitSelectionConfirmed(selectedDataUris: MutableList<String>) {
//        TODO("Not yet implemented")
//    }

//    private fun toggleListView() {
//        if (adapter?.getItemCount() === 0) {
//            noSelectionView!!.visibility = View.VISIBLE
//            selectedItemsListView!!.visibility = View.GONE
//        } else {
//            noSelectionView!!.visibility = View.GONE
//            selectedItemsListView!!.visibility = View.VISIBLE
//        }
//    }
//
//    override fun onGalleryKitBackAction() {
//        galleryPostActivity?.toggleGalleryFragment(false)
//    }
//
//    override fun onGalleryKitSelectionConfirmed(selectedDataUris: MutableList<String>) {
//        adapter?.setAllData(selectedDataUris)
//        galleryPostActivity?.toggleGalleryFragment(false)
//        toggleListView()    }
//
//    fun getSelectedData(): List<String> {
//        return adapter?.getSelectedDataList()!!
//    }

}


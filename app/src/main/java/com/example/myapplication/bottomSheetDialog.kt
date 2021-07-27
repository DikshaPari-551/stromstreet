package com.example.myapplication

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class bottomSheetDialog : BottomSheetDialogFragment() {
    var pic_id = 123
    private val pickImage = 100
    lateinit var cancel:TextView
     lateinit var gallery:TextView
    lateinit var camera: TextView
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var v = inflater.inflate(R.layout.bottom_drawer, container, false)
        gallery=v.findViewById(R.id.gallery_open)
        camera = v.findViewById(R.id.camera_open)
        cancel=v.findViewById(R.id.cancel)
        var bottomsheet=bottomSheetDialog()

        cancel.setOnClickListener{
            bottomsheet.dismiss()

        }
        gallery.setOnClickListener{
            val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            startActivityForResult(gallery, pickImage)
        }
        camera.setOnClickListener {
            val camera_intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivity(camera_intent);
        }
        return v
    }

}
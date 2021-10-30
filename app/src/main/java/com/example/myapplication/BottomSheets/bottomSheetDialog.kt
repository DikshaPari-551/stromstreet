package com.example.myapplication

import android.app.Dialog
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.FileProvider
import com.example.myapplication.customclickListner.ClickListner
import com.example.myapplication.entity.ApiCallBack
import com.example.myapplication.entity.Response.Responce
import com.example.myapplication.entity.Service_Base.ServiceManager
import com.example.myapplication.util.AppConst
import com.example.myapplication.util.ImageCount
import com.example.myapplication.util.SavedPrefManager
//import com.github.chiragji.gallerykit.callbacks.GalleryKitListener
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class bottomSheetDialog(
    var flag: String,
    var myClickListner: ClickListner
) :
    BottomSheetDialogFragment() {
    var pic_id = 123
    private val pickImage = 100
    lateinit var cancel: TextView
    lateinit var gallery: TextView
    lateinit var camera: TextView
    private val GALLERY = 1
    private var CAMERA: Int = 2
    private var openFlag = ""
    var imageList: ArrayList<Bitmap?> = ArrayList()
    lateinit var bitmap: Bitmap
    val threeImageFlag = 0
    lateinit var image: Uri
    lateinit var mContext: Context
    lateinit var imageFile: File
    var imagePath = ""
    lateinit var serviceManager: ServiceManager
    lateinit var callBack: ApiCallBack<Responce>
    val CAMERA_PERM_CODE = 101
    var photoURI: Uri? = null
    companion object {
        var count = 0
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var v = inflater.inflate(R.layout.bottom_drawer, container, false)
        mContext = activity!!.applicationContext
        serviceManager = ServiceManager(mContext)
        gallery = v.findViewById(R.id.gallery_open)
        camera = v.findViewById(R.id.camera_open)
        cancel = v.findViewById(R.id.cancel)
        cancel.setOnClickListener {
            dismiss()
        }


        gallery.setOnClickListener { view: View? ->
            if (flag == "addpost") {
                    val intent = Intent(Intent.ACTION_GET_CONTENT)
                    intent.addCategory(Intent.CATEGORY_OPENABLE)
                    intent.type = "image/*"
                    startActivityForResult(
                        Intent.createChooser(intent, "Select Picture"),
                        GALLERY
                    )

            } else {
                val intent = Intent(Intent.ACTION_GET_CONTENT)
                intent.addCategory(Intent.CATEGORY_OPENABLE)
                intent.type = "image/*"
                startActivityForResult(
                    Intent.createChooser(intent, "Select Picture"),
                    GALLERY
                )
            }
        }

        camera.setOnClickListener {
            if (flag == "addpost") {
                    dispatchTakePictureIntent()
            } else {
                dispatchTakePictureIntent()
            }
        }
        return v
    }

    override fun getTheme(): Int = R.style.BottomSheetDialogTheme
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog =
        BottomSheetDialog(requireContext(), theme)

    fun choosePhotoFromGallary() {
        val intent = Intent(
            Intent.ACTION_PICK,
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        )
        intent.type = "image/* video/*"
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, intent.type), GALLERY)

    }

    private fun dispatchTakePictureIntent() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (takePictureIntent.resolveActivity(mContext.getPackageManager()) != null) {
            try {
                imageFile = createImageFile()!!
            } catch (ex: IOException) {
            }
            // Continue only if the File was successfully created
            if (imageFile != null) {
                photoURI = FileProvider.getUriForFile(
                    mContext,
                    "com.example.myapplication.fileprovider",
                    imageFile
                )
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                startActivityForResult(takePictureIntent, CAMERA)
            }
        }
    }

    @Throws(IOException::class)
    private fun createImageFile(): File? {
        // Create an image file name
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val imageFileName = "JPEG_" + timeStamp + "_"
        //        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        val storageDir =
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
        val image = File.createTempFile(
            imageFileName,  /* prefix */
            ".jpg",  /* suffix */
            storageDir /* directory */
        )

        // Save a file: path for use with ACTION_VIEW intents
        imagePath = image.absolutePath
        return image
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (flag == "addpost") {

            myClickListner.clickListner(requestCode, resultCode, data, this, imagePath)

        } else if (flag == "signup") {

            myClickListner.clickListner(requestCode, resultCode, data, this, imagePath)

        } else if (flag == "profilechange") {

            myClickListner.clickListner(requestCode, resultCode, data, this, imagePath)

        }
    }

}


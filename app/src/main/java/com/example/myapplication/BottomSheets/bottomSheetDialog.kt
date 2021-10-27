package com.example.myapplication

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.os.StrictMode
import android.os.StrictMode.VmPolicy
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import com.example.myapplication.customclickListner.ClickListner
import com.example.myapplication.entity.ApiCallBack
import com.example.myapplication.entity.Response.Responce
import com.example.myapplication.entity.Service_Base.ServiceManager
import com.example.myapplication.util.AppConst
import com.example.myapplication.util.SavedPrefManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.io.*
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
    private val IMAGE_DIRECTORY = "/demonuts"
    private var openFlag = ""
    var imageList: ArrayList<Bitmap?> = ArrayList()
    lateinit var bitmap: Bitmap
    val threeImageFlag = 0
    lateinit var image: Uri
    lateinit var mContext : Context
    lateinit var imageFile: File
    var imagePath =""
    lateinit var serviceManager: ServiceManager
    lateinit var callBack: ApiCallBack<Responce>


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
//        var bottomsheet=bottomSheetDialog()

        cancel.setOnClickListener {
            dismiss()
        }


        gallery.setOnClickListener { view: View? ->
            if (flag == "addpost") {
                if (SavedPrefManager.getStringPreferences(activity, AppConst.IMAGEDATA) == "true") {
                    Toast.makeText(
                        activity,
                        "Not add more than 3 photos",
                        Toast.LENGTH_SHORT
                    ).show()
                    imageList.clear()
                } else {
                    choosePhotoFromGallary()
                }
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
                if (SavedPrefManager.getStringPreferences(activity, AppConst.IMAGEDATA) == "true") {
                    Toast.makeText(
                        activity,
                        "Not add more than 3 photos",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    imagePath = ""+System.currentTimeMillis() + ".jpg";
                    takePhotoFromCamera()
                }
            } else {
                imagePath = ""+System.currentTimeMillis() + ".jpg";
                takePhotoFromCamera()
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


    private fun takePhotoFromCamera() {
//        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
//        startActivityForResult(intent, CAMERA)
        val builder = VmPolicy.Builder()
        StrictMode.setVmPolicy(builder.build())
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        try {
            var mImageCaptureUri = Uri.fromFile(File(activity!!.getExternalFilesDir("temp"), imagePath))
            intent.putExtra(MediaStore.ACTION_IMAGE_CAPTURE, mImageCaptureUri)
            intent.putExtra("return-data", true)
            startActivityForResult(intent, CAMERA)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (flag == "addpost") {

            myClickListner.clickListner(requestCode, resultCode, data, this,imagePath)

        } else if (flag == "signup") {

            myClickListner.clickListner(requestCode, resultCode, data, this,imagePath)

        } else if (flag == "profilechange") {

            myClickListner.clickListner(requestCode, resultCode, data, this,imagePath)

        }

    }

//    private fun changeProfile(requestCode: Int, resultCode: Int, data: Intent?) {
//        if (resultCode == Activity.RESULT_CANCELED) {
//            return
//        }
//        if (resultCode == Activity.RESULT_CANCELED) {
//            return
//        }
//        try {
//            if (requestCode == GALLERY) {
//                if (resultCode == Activity.RESULT_OK) {
//                    if (data != null) {
//                        image = data.data!!
//                        val path = getPathFromURI(image)
//                        if (path != null) {
//                            imageFile = File(path)
//                            image = Uri.fromFile(imageFile)
//
//                        }
//                    }
//                }
//            } else if (requestCode == CAMERA) {
//                if (resultCode == Activity.RESULT_OK) {
//                    if (data != null) {
//                        image = data?.extras?.get("data")!! as Uri
//                        val path = getPathFromURI(image)
//                        if (path != null) {
//                            imageFile = File(path)
//                            image = Uri.fromFile(imageFile)
//
//                        }
//                    }
//                }
//            }
//            try {
//                moveDataAnotherScreen(image, imageList)
//            } catch (e: IOException) {
//                e.printStackTrace()
//                Toast.makeText(activity, "Failed!", Toast.LENGTH_SHORT).show()
//            }
//
//        } catch (e: java.lang.Exception) {
//            e.printStackTrace()
//        }
//    }
//
//    private fun signUpProfileUpload(requestCode: Int, resultCode: Int, data: Intent?) {
//        if (resultCode == Activity.RESULT_CANCELED) {
//            return
//        }
//        try {
//            if (requestCode == GALLERY) {
//                if (resultCode == Activity.RESULT_OK) {
//                    if (data != null) {
//                        image = data.data!!
//                        val path = getPathFromURI(image)
//                        if (path != null) {
//                            imageFile = File(path)
////                            FileUpload.setImageFile(f)
//                            image = Uri.fromFile(imageFile)
//
//                        }
//                    }
//                }
//            } else if (requestCode == CAMERA) {
//                if (resultCode == Activity.RESULT_OK) {
//                    if (data != null) {
//                        image = data.data!!
//                        val path = getPathFromURI(image)
//                        if (path != null) {
//                            imageFile = File(path)
////                            FileUpload.setImageFile(f)
//                            image = Uri.fromFile(imageFile)
//
//                        }
//                    }
//                }
//            }
//            try {
//                moveDataAnotherScreen(image, imageList)
//            } catch (e: IOException) {
//                e.printStackTrace()
//                Toast.makeText(activity, "Failed!", Toast.LENGTH_SHORT).show()
//            }
//
//        } catch (e: java.lang.Exception) {
//            e.printStackTrace()
//        }
//
//    }
//
//    private fun addPostMediaUpload(requestCode: Int, resultCode: Int, data: Intent?) {
//        try {
//            if (resultCode == Activity.RESULT_CANCELED) {
//                return
//            }
//            try {
//                if (requestCode == GALLERY) {
//                    if (data!!.clipData != null) {
//                        if (data!!.clipData!!.itemCount > 3) {
//                            Toast.makeText(
//                                activity,
//                                "Not select more than 3 photos",
//                                Toast.LENGTH_SHORT
//                            ).show()
//                        } else {
//                            var clipDataCount: Int = data.clipData!!.itemCount
//                            for (i in 0 until clipDataCount) {
//                                var imageUri: Uri = data.getClipData()!!.getItemAt(i).getUri()
//                                bitmap =
//                                    MediaStore.Images.Media.getBitmap(
//                                        activity?.contentResolver,
//                                        imageUri
//                                    )
//                                imageList.add(bitmap)
//                            }
//                        }
//                    } else if (data != null && data.clipData == null) {
//                        var imageUri: Uri = data.data!!
//                        bitmap =
//                            MediaStore.Images.Media.getBitmap(
//                                activity?.contentResolver,
//                                imageUri
//                            )
//                        imageList.add(bitmap)
//                    }
//                    try {
//                        moveDataAnotherScreen(image, imageList)
//                    } catch (e: IOException) {
//                        e.printStackTrace()
//                        Toast.makeText(activity, "Failed!", Toast.LENGTH_SHORT).show()
//                    }
//
//                } else if (requestCode == CAMERA) {
//                    if (data?.extras != null) {
//                        val thumbnail: Bitmap = data?.extras?.get("data") as Bitmap
//                        val bundle = Bundle()
//                        bundle.putParcelable("BitmapImage", thumbnail)
//                        val fragobj = AddPostFragment()
//                        fragobj.setArguments(bundle)
//                        fragmentManager?.beginTransaction()?.replace(R.id.linear_layout, fragobj)
//                            ?.commit()
//                        dismiss()
//                        Toast.makeText(activity, "Image Saved!", Toast.LENGTH_SHORT).show()
//                    }
//                }
//            } catch (e: Exception) {
//                e.printStackTrace()
//            }
//        } catch (e: KotlinNullPointerException) {
//            e.printStackTrace()
//        }
//    }
//
//    private fun moveDataAnotherScreen(image: Uri, imageList: ArrayList<Bitmap?>) {
//        if (flag.equals("addpost")) {
//            if (imageList.size > 0) {
//                val fragobj = AddPostFragment()
////                fragobj.setArguments(bundle)
//                fragmentManager?.beginTransaction()
//                    ?.replace(R.id.linear_layout, fragobj)
//                    ?.commit()
//                dismiss()
//            }
//        } else if (flag == "signup") {
//            SavedPrefManager.saveStringPreferences(
//                activity,
//                AppConst.USER_IMAGE_UPLOADED,
//                "true"
//            )
//            circleProfile?.setImageURI(image)
//            uploadUserImageApi()
//            dismiss()
//        } else if (flag == "profilechange") {
//            SavedPrefManager.saveStringPreferences(
//                activity,
//                AppConst.USER_IMAGE_UPLOADED,
//                "true"
//            )
//            circleProfile?.setImageURI(image)
//            FileUpload.setImageFile(image)
//            dismiss()
//            uploadUserImageApi()
//            fragmentManager?.beginTransaction()
//                ?.replace(R.id.linear_layout,ProfileChangeFragment())
//                ?.commit()
//            dismiss()
//        }
//    }
//
//
//    fun saveImage(myBitmap: Bitmap): String? {
//        val bytes = ByteArrayOutputStream()
//        myBitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes)
//        val wallpaperDirectory = File(
//            Environment.getExternalStorageState() + IMAGE_DIRECTORY
//        )
//
//        if (!wallpaperDirectory.exists()) {
//            wallpaperDirectory.mkdirs()
//        }
//        try {
//            val f = File(
//                wallpaperDirectory, Calendar.getInstance()
//                    .getTimeInMillis().toString() + ".jpg"
//            )
//            f.createNewFile()
//            val fo = FileOutputStream(f)
//            fo.write(bytes.toByteArray())
//            MediaScannerConnection.scanFile(
//                activity,
//                arrayOf(f.getPath()),
//                arrayOf("image/jpeg"),
//                null
//            )
//            fo.close()
//            Log.d("TAG", "File Saved::--->" + f.getAbsolutePath())
//            return f.getAbsolutePath()
//        } catch (e1: IOException) {
//            e1.printStackTrace()
//        }
//        return ""
//
//    }
//
//    private fun requestMultiplePermissions() {
//        Dexter.withActivity(activity)
//            .withPermissions(
//                android.Manifest.permission.CAMERA,
//                android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
//                android.Manifest.permission.READ_EXTERNAL_STORAGE
//            )
//            .withListener(object : MultiplePermissionsListener {
//                override fun onPermissionsChecked(report: MultiplePermissionsReport) {
//                    // check if all permissions are granted
//                    if (report.areAllPermissionsGranted()) {
//                        Toast.makeText(
//                            activity,
//                            "All permissions are granted by user!",
//                            Toast.LENGTH_SHORT
//                        ).show()
//                    }
//
//                    // check for permanent denial of any permission
//                    if (report.isAnyPermissionPermanentlyDenied) {
//                        // show alert dialog navigating to Settings
//                        //openSettingsDialog();
//                    }
//                }
//
//                override fun onPermissionRationaleShouldBeShown(
//                    permissions: MutableList<com.karumi.dexter.listener.PermissionRequest>?,
//                    token: PermissionToken?
//                ) {
//                    token?.continuePermissionRequest()
//                }
//
//            }).withErrorListener {
//                Toast.makeText(
//                    activity,
//                    "Some Error! ",
//                    Toast.LENGTH_SHORT
//                ).show()
//            }
//            .onSameThread()
//            .check()
//    }
//
//
//



}


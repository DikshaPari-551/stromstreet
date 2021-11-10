package com.example.myapplication

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.provider.MediaStore.Files.FileColumns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.FileProvider
import com.example.myapplication.customclickListner.ClickListner
import com.example.myapplication.entity.permission.MarshMallowPermission
//import com.github.chiragji.gallerykit.callbacks.GalleryKitListener
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.io.File
import java.text.SimpleDateFormat
import java.util.*


class AddBottomSheetDialog(
    var s: String,
    var mainActivity: ClickListner,
    var marshMallowPermission1: Activity
) :
    BottomSheetDialogFragment() {
    lateinit var mContext: Context
    lateinit var openGalleryPhoto: TextView
    lateinit var openGalleryVideo: TextView
    lateinit var openCameraPhoto: TextView
    lateinit var openCameraVideo: TextView
    lateinit var cancel: TextView
    private var fileUploadPath = ""
    private val IMAGE_DIRECTORY_NAME = "StromeStreet"
    private var fileUri: Uri? = null
    protected val CAMERA_REQUEST = 3


    var marshMallowPermission: MarshMallowPermission? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var v = inflater.inflate(R.layout.add_bottom_drawer, container, false)
        mContext = activity!!.applicationContext
        openGalleryPhoto = v.findViewById<TextView>(R.id.gallery_open)
        openGalleryVideo = v.findViewById<TextView>(R.id.gallery_video)
        openCameraPhoto = v.findViewById<TextView>(R.id.camera_open)
        openCameraVideo = v.findViewById<TextView>(R.id.camera_video)
        cancel = v.findViewById<TextView>(R.id.cancel)
        marshMallowPermission = MarshMallowPermission(marshMallowPermission1)


        openCameraPhoto.setOnClickListener{
            if (!marshMallowPermission!!.checkPermissionForCamera()) {
                marshMallowPermission!!.requestPermissionForCamera()
            } else {
                if (!marshMallowPermission!!.checkPermissionForExternalStorage()) {
                    marshMallowPermission!!.requestPermissionForExternalStorage()
                } else {
                    if (Build.VERSION.SDK_INT >= 24) {
                        val captureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                        fileUploadPath = getOutputMediaFile(FileColumns.MEDIA_TYPE_IMAGE)!!.getAbsolutePath()
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
                            //  fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);
                            fileUri = FileProvider.getUriForFile(
                                mContext,
                                BuildConfig.APPLICATION_ID + ".fileprovider",
                                File(fileUploadPath)
                            )
                            captureIntent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri)
                        } else {
                            fileUri = FileProvider.getUriForFile(
                                mContext,
                                BuildConfig.APPLICATION_ID + ".fileprovider",
                                File(fileUploadPath)
                            )
                            captureIntent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri)
                        }
                        startActivityForResult(
                            Intent.createChooser(
                                captureIntent,
                                "select image from camera"
                            ), CAMERA_REQUEST
                        )
                    } else {
                        val captureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
                            fileUri = FileProvider.getUriForFile(
                                mContext,
                                BuildConfig.APPLICATION_ID + ".fileprovider",
                                File(fileUploadPath)
                            )
                            captureIntent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri)
                        } else {
                            fileUri = FileProvider.getUriForFile(
                                mContext,
                                BuildConfig.APPLICATION_ID + ".fileprovider",
                                File(fileUploadPath)
                            )
                            captureIntent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri)
                        }
                        startActivityForResult(
                            Intent.createChooser(
                                captureIntent,
                                "select image from camera"
                            ), CAMERA_REQUEST
                        )
                    }
                }
            }
        }

        openCameraVideo.setOnClickListener{

        }

        openGalleryPhoto.setOnClickListener{

        }

        openGalleryVideo.setOnClickListener{

        }

        cancel.setOnClickListener{
            dismiss()
        }


        return v
    }

    override fun getTheme(): Int = R.style.BottomSheetDialogTheme
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog =
        BottomSheetDialog(requireContext(), theme)


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
//        mainActivity.clickListner(requestCode,resultCode,data,mContext,fileUploadPath)

    }
    private fun getOutputMediaFile(type: Int): File? {
        // External sdcard locationasasa
        val mediaStorageDir = File(
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
            IMAGE_DIRECTORY_NAME
        )
        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                // Log.d(IMAGE_DIRECTORY_NAME, "Oops! Failed create " +
                // IMAGE_DIRECTORY_NAME + " directory");
                return null
            }
        }
        // Create a media file name
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val mediaFile: File
        mediaFile = if (type == FileColumns.MEDIA_TYPE_IMAGE) {
            File(mediaStorageDir.path + File.separator + "IMG_" + timeStamp + ".jpg")
        } else {
            return null
        }
        return mediaFile
    }


}


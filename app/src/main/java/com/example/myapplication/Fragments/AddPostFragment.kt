package com.example.myapplication.Fragments

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.bottomSheetDialog
import com.example.myapplication.entity.ApiCallBack
import com.example.myapplication.entity.Request.Api_Request
import com.example.myapplication.entity.Request.Location
import com.example.myapplication.entity.Response.Responce
import com.example.myapplication.entity.Service_Base.ApiResponseListener
import com.example.myapplication.entity.Service_Base.ServiceManager
import com.example.myapplication.extension.androidextention
import com.example.myapplication.util.AppConst
import com.example.myapplication.util.ImageCount
import com.example.myapplication.util.SavedPrefManager
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.InputStream


class AddPostFragment(
    var requestCode: Int,
    var resultCode: Int,
    var data: Intent?,
    var bottomSheetDialog: bottomSheetDialog,
    var imagePath: String
) : Fragment() {
    private lateinit var spin: Spinner
    private lateinit var galleryData1: ImageView
    private lateinit var galleryData2: ImageView
    private lateinit var galleryData3: ImageView
    private lateinit var description: EditText
    private lateinit var postBackButton: ImageView
    private lateinit var spinDropDown: ImageView
    private lateinit var post: LinearLayout
    var HorizontalLayout: LinearLayoutManager? = null
    lateinit var mContext: Context
    private var postDescriptionText = ""
    var categoryItem: ArrayList<String?> = ArrayList()
    lateinit var serviceManager: ServiceManager
    lateinit var callBack: ApiCallBack<Responce>
    lateinit var imageData: MultipartBody.Part
    private val GALLERY = 1
    private var CAMERA: Int = 2
    lateinit var image: Uri
    lateinit var imageFile: File
    var imageList: ArrayList<Bitmap?> = ArrayList()
    var responseImageList: ArrayList<String> = ArrayList()
    var fileImageList: ArrayList<File> = ArrayList()
    var uriImageList: ArrayList<Uri> = ArrayList()
    var imageparts: ArrayList<MultipartBody.Part> = ArrayList()
    val MAX_IMAGE = 3
    var fileFlag = ""
    var uploaded_file: MultipartBody.Part? = null
    private var imageType = ""
    var againCondition: Boolean = true
    val CAMERA_PERM_CODE = 101


    companion object {
        var count = 0
        var maxCont = 0
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view: View = inflater.inflate(R.layout.fragment_add_post, container, false)
        serviceManager = ServiceManager(activity)
        mContext = activity!!.applicationContext
        post = view.findViewById(R.id.post)
        galleryData1 = view.findViewById(R.id.image_1)
        galleryData2 = view.findViewById(R.id.image_2)
        galleryData3 = view.findViewById(R.id.image_3)
        description = view.findViewById(R.id.post_description)
        postBackButton = view.findViewById(R.id.post_back_button)
        spinDropDown = view.findViewById(R.id.spinDropDown)
        spin = view.findViewById(R.id.spinner2)
//api
        categoryListApi()
        addPostData(requestCode, resultCode, data, bottomSheetDialog, imagePath)

        postBackButton.setOnClickListener {
            SavedPrefManager.saveStringPreferences(activity, AppConst.IMAGEDATA, "false")
            ImageCount.setImageCount(com.example.myapplication.bottomSheetDialog.count++)
            fragmentManager?.beginTransaction()?.replace(
                R.id.linear_layout,
                HomeFragment()
            )?.commit()
        }

        post.setOnClickListener {
            SavedPrefManager.saveStringPreferences(activity, AppConst.IMAGEDATA, "false")
            ImageCount.setImageCount(0)
            addPost()

        }


        return view
    }


    private fun categoryListApi() {
//        androidextention.showProgressDialog(mContext)
        callBack =
            ApiCallBack<Responce>(object : ApiResponseListener<Responce> {
                override fun onApiSuccess(response: Responce, apiName: String?) {
//                    androidextention.disMissProgressDialog(mContext)
                    if (response.responseCode == "200") {
                        for (i in 0 until response.result.categoryResult.size) {
                            categoryItem.add(response.result.categoryResult.get(i).categoryName)
                            SavedPrefManager.saveStringPreferences(
                                activity,
                                AppConst.POST_CATEGORY_ID,
                                response.result.categoryResult.get(i)._id
                            )
                        }
                        setSpinnerAdapter(categoryItem)
                    } else {
                        Toast.makeText(
                            activity,
                            response.responseMessage,
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }

                override fun onApiErrorBody(response: ResponseBody?, apiName: String?) {
//                    androidextention.disMissProgressDialog(mContext)
                    Toast.makeText(
                        activity,
                        "error response" + response.toString(),
                        Toast.LENGTH_LONG
                    ).show()
                }

                override fun onApiFailure(failureMessage: String?, apiName: String?) {
//                    androidextention.disMissProgressDialog(mContext)
                    Toast.makeText(
                        activity,
                        "failure response:" + failureMessage,
                        Toast.LENGTH_LONG
                    ).show()
                }

            }, "CategoryList", mContext)

        try {
            serviceManager.getCategoryList(callBack)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun uploadUserImageApi() {
        androidextention.showProgressDialog(mContext)
        callBack =
            ApiCallBack<Responce>(object : ApiResponseListener<Responce> {
                override fun onApiSuccess(response: Responce, apiName: String?) {
                    androidextention.disMissProgressDialog(mContext)
                    if (response.responseCode == "200") {
                        responseImageList.add(response.result.mediaUrl)
                        imageType = response.result.mediaType

                    } else {
                        Toast.makeText(
                            mContext,
                            response.responseMessage,
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }

                override fun onApiErrorBody(response: ResponseBody?, apiName: String?) {
                    androidextention.disMissProgressDialog(mContext)
                    Toast.makeText(
                        mContext,
                        "error response" + response.toString(),
                        Toast.LENGTH_LONG
                    ).show()
                }

                override fun onApiFailure(failureMessage: String?, apiName: String?) {
                    androidextention.disMissProgressDialog(mContext)
                    Toast.makeText(
                        mContext,
                        "fa" +
                                "ilure response:" + failureMessage,
                        Toast.LENGTH_LONG
                    ).show()
                }

            }, "UploadFile", mContext)

//        var surveyBody = RequestBody.create("image/*".toMediaTypeOrNull(), imageFile)
//        var uploaded_file: MultipartBody.Part =
//            MultipartBody.Part.createFormData("image", imageFile.name, surveyBody)


        try {
            if (fileFlag == "gallery_with_mutiple") {
                serviceManager.addUpost(callBack, imageparts)
            } else if (fileFlag == "single_image") {
                serviceManager.userUploadFile(callBack, uploaded_file)

            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


    private fun addPost() {
        androidextention.showProgressDialog(activity)
        callBack =
            ApiCallBack<Responce>(object : ApiResponseListener<Responce> {
                override fun onApiSuccess(response: Responce, apiName: String?) {
                    androidextention.disMissProgressDialog(mContext)
                    if (response.responseCode == "200") {
                        Toast.makeText(
                            activity,
                            response.responseMessage,
                            Toast.LENGTH_LONG
                        ).show()
                        fragmentManager?.beginTransaction()?.replace(
                            R.id.linear_layout,
                            HomeFragment()
                        )?.commit()

                    } else {
                        Toast.makeText(
                            activity,
                            response.responseMessage,
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }

                override fun onApiErrorBody(response: ResponseBody?, apiName: String?) {
                    androidextention.disMissProgressDialog(activity)
                    Toast.makeText(
                        activity,
                        "error response" + response.toString(),
                        Toast.LENGTH_LONG
                    ).show()
                }

                override fun onApiFailure(failureMessage: String?, apiName: String?) {
                    androidextention.disMissProgressDialog(activity)
                    Toast.makeText(
                        activity,
                        "failure response:" + failureMessage,
                        Toast.LENGTH_LONG
                    ).show()
                }

            }, "AddPost", mContext)

        val apiRequest = Api_Request()
        val location = Location("Point", arrayListOf(SavedPrefManager.getLatitudeLocation(), SavedPrefManager.getLongitudeLocation()))
        apiRequest.mediaType = imageType.toUpperCase()
        apiRequest.description = description.text.toString()
        apiRequest.categoryId =
            SavedPrefManager.getStringPreferences(activity, AppConst.POST_CATEGORY_ID)
        apiRequest.videoLink = "video_link"
        apiRequest.address="xyz"
        apiRequest.imageLinks = responseImageList
        apiRequest.location = location
        try {
            serviceManager.userAddPost(callBack, apiRequest)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


    private fun setSpinnerAdapter(spinnerData: ArrayList<String?>) {
        val adapter: ArrayAdapter<*> = ArrayAdapter<Any?>(
            activity!!,
            android.R.layout.simple_list_item_1,
            spinnerData as List<Any?>
        )

        spin.adapter = adapter
    }


    fun StringToBitMap(image: String?): Bitmap? {
        return try {
            val encodeByte: ByteArray = Base64.decode(image, Base64.DEFAULT)
            val inputStream: InputStream = ByteArrayInputStream(encodeByte)
            BitmapFactory.decodeStream(inputStream)
        } catch (e: Exception) {
            e.message
            null
        }
    }


    private fun addPostData(
        requestCode: Int,
        resultCode: Int,
        data: Intent?,
        bottomSheetDialog: bottomSheetDialog,
        imagePath: String
    ) {
        try {
            if (resultCode == Activity.RESULT_CANCELED) {
//                return
                Toast.makeText(activity,
                    "User cancelled image capture", Toast.LENGTH_SHORT)
                    .show();
                bottomSheetDialog.dismiss()
            } else {
                try {
                    if (requestCode == GALLERY) {
                        if (data!!.clipData != null) {
                                fileFlag = "gallery_with_mutiple"

                                var clipDataCount: Int = data!!.clipData!!.itemCount
                                for (i in 0 until clipDataCount) {
                                    var imageUri: Uri = data.getClipData()!!.getItemAt(i).getUri()
                                    var bitmap =
                                        MediaStore.Images.Media.getBitmap(
                                            activity?.contentResolver,
                                            imageUri
                                        )
                                    imageList.add(bitmap)


                                    var tempUri: Uri =
                                        getImageUri(activity!!.applicationContext, bitmap)!!

                                    val path = getPathFromURI(tempUri)
                                    if (path != null) {
                                        imageFile = File(path)
                                    }
                                    var requestGallaryImageFile: RequestBody =
                                        RequestBody.create("image/*".toMediaTypeOrNull(), imageFile)
                                    imageparts.add(
                                        MultipartBody.Part.createFormData(
                                            "image",
                                            imageFile.getName(),
                                            requestGallaryImageFile
                                        )
                                    )
                                }
                                galleryData1.setImageBitmap(imageList[0])
                                galleryData2.setImageBitmap(imageList[1])
                                galleryData3.setImageBitmap(imageList[2])
                                bottomSheetDialog.dismiss()

                                uploadUserImageApi()
                        } else if (data != null && data!!.clipData == null) {
                            fileFlag = "single_image"
                            image = data.data!!

                            if (galleryData1.visibility == View.VISIBLE || count >= 1) {
                                if (galleryData2.visibility == View.VISIBLE || count >= 2) {
                                    if (galleryData3.visibility == View.VISIBLE || count >= 3) {
                                        Toast.makeText(
                                            activity,
                                            "Not select more than 3 photos",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    } else {
                                        count = 0
                                        SavedPrefManager.saveStringPreferences(activity, AppConst.IMAGEDATA, "true")
                                        galleryData3.visibility = View.VISIBLE
                                        galleryData3.setImageURI(image)
                                    }
                                } else {
                                    count++
                                    galleryData2.visibility = View.VISIBLE
                                    galleryData2.setImageURI(image)
                                }
                            } else {
                                count++
                                galleryData1.visibility = View.VISIBLE
                                galleryData1.setImageURI(image)

                            }
                            bottomSheetDialog.dismiss()
                            val path = getPathFromURI(image)
                            if (path != null) {
                                imageFile = File(path)
                            }
                            var surveyBody =
                                RequestBody.create("image/*".toMediaTypeOrNull(), imageFile)
                            uploaded_file =
                                MultipartBody.Part.createFormData(
                                    "image",
                                    imageFile.name,
                                    surveyBody
                                )
                            uploadUserImageApi()
                        }

                    } else if (requestCode == CAMERA) {
                        fileFlag = "single_image"
                            imageFile = File(imagePath)
                            uriImageList.add(Uri.fromFile(imageFile))


                            if (galleryData1.visibility == View.GONE || count >= 1) {

                                if (galleryData2.visibility == View.GONE || count >= 2) {
                                    if (galleryData3.visibility == View.GONE || count >= 3) {
                                        Toast.makeText(
                                            activity,
                                            "Not select more than 3 photos",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    } else {
                                        count = 0

//                                        galleryData3.setImageURI(Uri.fromFile(imageFile))
                                        SavedPrefManager.saveStringPreferences(mContext, SavedPrefManager.IMAGE_THREE,imageFile.toString())

//                                        get
                                        var imageThree = SavedPrefManager.getStringPreferences(mContext,SavedPrefManager.IMAGE_THREE)
                                        var imageFThree = File(imageThree)
                                        galleryData3.visibility = View.VISIBLE
                                        galleryData3.setImageURI(Uri.fromFile(imageFThree))
                                        var imagef = SavedPrefManager.getStringPreferences(mContext,SavedPrefManager.IMAGE_ONE)
                                        var imageFOne = File(imagef)
                                        galleryData1.visibility = View.VISIBLE
                                        galleryData1.setImageURI(Uri.fromFile(imageFOne))
                                        var imageTwo = SavedPrefManager.getStringPreferences(mContext,SavedPrefManager.IMAGE_TWO)
                                        var imageFTwo = File(imageTwo)
                                        galleryData2.visibility = View.VISIBLE
                                        galleryData2.setImageURI(Uri.fromFile(imageFTwo))


                                    }
                                } else {
                                    count++

                                    galleryData2.visibility = View.VISIBLE
//                                    galleryData2.setImageURI(Uri.fromFile(imageFile))
                                    SavedPrefManager.saveStringPreferences(mContext, SavedPrefManager.IMAGE_TWO,imageFile.toString())

//                                    get
                                    var imageTwo = SavedPrefManager.getStringPreferences(mContext,SavedPrefManager.IMAGE_TWO)
                                    var imageFTwo = File(imageTwo)
                                    galleryData2.visibility = View.VISIBLE
                                    galleryData2.setImageURI(Uri.fromFile(imageFTwo))
                                    var imageOne = SavedPrefManager.getStringPreferences(mContext,SavedPrefManager.IMAGE_ONE)
                                    var image = File(imageOne)
                                    galleryData1.visibility = View.VISIBLE
                                    galleryData1.setImageURI(Uri.fromFile(image))

                                }
                            } else {
                                count++
                                galleryData1.visibility = View.VISIBLE
//                                galleryData1.setImageURI(Uri.fromFile(imageFile))
                                SavedPrefManager.saveStringPreferences(mContext, SavedPrefManager.IMAGE_ONE,imageFile.toString())

//                                get
                                var imageOne = SavedPrefManager.getStringPreferences(mContext,SavedPrefManager.IMAGE_ONE)
                                var image = File(imageOne)
                                galleryData1.visibility = View.VISIBLE
                                galleryData1.setImageURI(Uri.fromFile(image))


                            }
                            bottomSheetDialog.dismiss()
                            var surveyBody =
                                RequestBody.create("image/*".toMediaTypeOrNull(), imageFile)
                            uploaded_file =
                                MultipartBody.Part.createFormData(
                                    "image",
                                    imageFile.name,
                                    surveyBody
                                )
                            uploadUserImageApi()

                        }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        } catch (e: KotlinNullPointerException) {
            e.printStackTrace()
        }
    }


    fun getImageUri(inContext: Context, inImage: Bitmap): Uri? {
        val bytes = ByteArrayOutputStream()
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
        val path =
            MediaStore.Images.Media.insertImage(inContext.contentResolver, inImage, "Title", null)
        return Uri.parse(path)
    }

    fun getPathFromURI(contentUri: Uri?): String? {
        var res: String? = null
        val proj = arrayOf(MediaStore.Images.Media.DATA)
        val cursor: Cursor? =
            activity!!.getContentResolver().query(contentUri!!, proj, null, null, null)
        if (cursor!!.moveToFirst()) {
            val column_index: Int = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
            res = cursor.getString(column_index)
        }
        cursor.close()
        return res
    }

}


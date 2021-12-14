package com.example.myapplication.Fragments

import android.app.Activity
import android.content.ContentResolver
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.graphics.Bitmap
import android.location.Address
import android.location.Geocoder
import android.media.ThumbnailUtils
import android.net.Uri
import android.os.*
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.abedelazizshe.lightcompressorlibrary.CompressionListener
import com.abedelazizshe.lightcompressorlibrary.VideoCompressor
import com.abedelazizshe.lightcompressorlibrary.VideoQuality
import com.abedelazizshe.lightcompressorlibrary.config.Configuration
import com.bumptech.glide.Glide
import com.example.myapplication.R
import com.example.myapplication.bottomSheetDialog
import com.example.myapplication.customclickListner.ClickListner
import com.example.myapplication.entity.ApiCallBack
import com.example.myapplication.entity.Request.Api_Request_AddPostpackage
import com.example.myapplication.entity.Request.Location
import com.example.myapplication.entity.Response.MediaResult
import com.example.myapplication.entity.Response.Responce
import com.example.myapplication.entity.Service_Base.ApiResponseListener
import com.example.myapplication.entity.Service_Base.ServiceManager
import com.example.myapplication.extension.androidextention
import com.example.myapplication.util.AppConst
import com.example.myapplication.util.SavedPrefManager
import com.example.sleeponcue.extension.getFileSize
import com.example.sleeponcue.extension.getMediaPath
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.*
import java.util.*
import kotlin.collections.ArrayList


class AddPostFragment() : Fragment(), ClickListner {
    private lateinit var spin: Spinner
    private lateinit var galleryData1: ImageView
    private lateinit var galleryData2: ImageView
    private lateinit var galleryData3: ImageView
    private lateinit var description: EditText
    private lateinit var postBackButton: ImageView
    private lateinit var spinDropDown: ImageView
    private lateinit var post: LinearLayout
    private lateinit var location: TextView
    lateinit var addImageOne: LinearLayout
    lateinit var addImageTwo: LinearLayout
    lateinit var addImageThree: LinearLayout
    lateinit var progressBarPost: ProgressBar
    var HorizontalLayout: LinearLayoutManager? = null
    lateinit var mContext: Context
    private var postDescriptionText = ""
    var categoryItem: ArrayList<String?> = ArrayList()
    var categoryId: ArrayList<String?> = ArrayList()
    lateinit var serviceManager: ServiceManager
    lateinit var callBack: ApiCallBack<Responce>
    lateinit var imageData: MultipartBody.Part
    private val GALLERY = 1
    private var CAMERA: Int = 2
    lateinit var image: Uri
    lateinit var imageFile: File
    val MAX_IMAGE = 3
    var fileFlag = ""
    var uploaded_file: MultipartBody.Part? = null
    private var imageType = ""
    private var videoLink = ""
    var againCondition: Boolean = true
    val CAMERA_PERM_CODE = 101
    var mime = ""
    lateinit var imageUri: Uri
    private var latitude: Double = 0.0
    private var longitude: Double = 0.0
    var locality: String = ""
    var bitmap: Bitmap? = null


    protected val CAPTURE_VIDEO_ACTIVITY_REQUEST_CODE = 3


    companion object {
        var count = 0
        var videoCount: Int = 0
        var maxCont = 0
        var imageList: ArrayList<Uri?> = ArrayList()
        var responseImageList: ArrayList<String> = ArrayList()
        var fileImageList: ArrayList<File> = ArrayList()
        var uriImageList: ArrayList<Uri> = ArrayList()
        var imageparts: ArrayList<MultipartBody.Part> = ArrayList()
        private lateinit var playableVideoPath: String
        private lateinit var compressVideo: File
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
        location = view.findViewById(R.id.location)
        addImageOne = view.findViewById(R.id.add_image_one)
        addImageTwo = view.findViewById(R.id.add_image_two)
        addImageThree = view.findViewById(R.id.add_image_Three)
        progressBarPost = view.findViewById(R.id.progress)
        try {
            latitude = SavedPrefManager.getLatitudeLocation()!!
            longitude = SavedPrefManager.getLongitudeLocation()!!
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
        count = 0
        videoCount = 0
        imageparts.clear()
        responseImageList.clear()

        SavedPrefManager.saveStringPreferences(
            mContext,
            SavedPrefManager.IMAGE_ONE,
            "image_one"
        )
        SavedPrefManager.saveStringPreferences(
            mContext,
            SavedPrefManager.IMAGE_TWO,
            "image_two"
        )
        SavedPrefManager.saveStringPreferences(
            mContext,
            SavedPrefManager.IMAGE_THREE,
            "image_three"
        )
        address()
        location.setText(locality)
//api
        categoryListApi()
//        imagePath?.let {
//            bottomSheetDialog?.let { it1 ->
//                resultCode?.let { it2 ->
//                    requestCode?.let { it3 ->
//                        addPostData(
//                            it3, it2, data,
//                            it1, it
//                        )
//                    }
//                }
//            }
//        }

        postBackButton.setOnClickListener {
            count = 0
            videoCount = 0
            imageparts.clear()
            responseImageList.clear()

            SavedPrefManager.saveStringPreferences(
                mContext,
                SavedPrefManager.IMAGE_ONE,
                "image_one"
            )
            SavedPrefManager.saveStringPreferences(
                mContext,
                SavedPrefManager.IMAGE_TWO,
                "image_two"
            )
            SavedPrefManager.saveStringPreferences(
                mContext,
                SavedPrefManager.IMAGE_THREE,
                "image_three"
            )
            fragmentManager?.beginTransaction()?.replace(
                R.id.linear_layout,
                HomeFragment()
            )?.commit()
            fragmentManager?.beginTransaction()?.replace(
                R.id.linear_layout,
                HomeFragment()
            )?.commit()
        }

        post.setOnClickListener {
            uploadMultipleData()

//            androidextention.showProgressDialog(activity)
//            if (imageparts.size > 0) {
//                for (i in 0 until imageparts.size) {
//                    uploadUserImageApi(imageparts[i])
//                }
//            }
        }

        spin.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View, pos: Int, id: Long
            ) {
                SavedPrefManager.saveStringPreferences(
                    activity,
                    AppConst.POST_CATEGORY_ID,
                    categoryId.get(pos)
                )
            }

            override fun onNothingSelected(arg0: AdapterView<*>?) {
            }
        }

        addImageOne.setOnClickListener {
            getImages()
        }

        addImageTwo.setOnClickListener {
            getImages()
        }

        addImageThree.setOnClickListener {
            getImages()
        }
        return view
    }


    private fun categoryListApi() {
        callBack =
            ApiCallBack<Responce>(object : ApiResponseListener<Responce> {
                override fun onApiSuccess(response: Responce, apiName: String?) {
                    if (response.responseCode == "200") {
                        for (i in 0 until response.result.categoryResult.size) {
                            categoryItem.add(response.result.categoryResult.get(i).categoryName)
                            categoryId.add(response.result.categoryResult.get(i)._id)
//                            SavedPrefManager.saveStringPreferences(
//                                activity,
//                                AppConst.POST_CATEGORY_ID,
//                                response.result.categoryResult.get(i)._id
//                            )
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

                override fun onApiErrorBody(response: String?, apiName: String?) {
                    Toast.makeText(
                        activity,
                        "error response" + response.toString(),
                        Toast.LENGTH_LONG
                    ).show()
                }

                override fun onApiFailure(failureMessage: String?, apiName: String?) {
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


    private fun uploadMultipleData() {
        androidextention.showProgressDialog(activity)
        callBack =
            ApiCallBack<Responce>(object : ApiResponseListener<Responce> {
                override fun onApiSuccess(response: Responce, apiName: String?) {
//                    androidextention.disMissProgressDialog(activity)
                    if (response.responseCode == "200") {
                        androidextention.showProgressDialog(activity)
                        var data: List<MediaResult> = response.result.mediaResult
                        Log.d("uploadResponse", data.toString())
                        for (i in 0 until data.size) {
                            imageType = data.get(i).mediaType
                            if (imageType == "image") {
                                responseImageList.add(data.get(i).mediaUrl)
                            } else {
                                videoLink = data.get(i).mediaUrl
                            }
                        }
                        addPost(data)


                    } else {
                        Toast.makeText(
                            mContext,
                            response.responseMessage,
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }

                override fun onApiErrorBody(response: String?, apiName: String?) {
                    androidextention.disMissProgressDialog(activity)
                    Toast.makeText(
                        mContext,
                        "Image or Video size is large. Size should be less then 30 MB. " + response.toString(),
                        Toast.LENGTH_LONG
                    ).show()
                }

                override fun onApiFailure(failureMessage: String?, apiName: String?) {
                    androidextention.disMissProgressDialog(activity)
                    Toast.makeText(
                        mContext,
                        "failure response:" + failureMessage,
                        Toast.LENGTH_LONG
                    ).show()
                }

            }, "UploadMultipleFile", mContext)

        try {
            serviceManager.uploadMultiData(callBack, imageparts)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


    private fun addPost(data: List<MediaResult>) {
        androidextention.showProgressDialog(activity)
        callBack =
            ApiCallBack<Responce>(object : ApiResponseListener<Responce> {
                override fun onApiSuccess(response: Responce, apiName: String?) {
                    androidextention.disMissProgressDialog(activity)
                    if (response.responseCode == "200") {
                        Toast.makeText(
                            activity,
                            response.responseMessage,
                            Toast.LENGTH_LONG
                        ).show()
                        count = 0
                        videoCount = 0
                        imageparts.clear()
                        responseImageList.clear()

                        SavedPrefManager.saveStringPreferences(
                            mContext,
                            SavedPrefManager.IMAGE_ONE,
                            "image_one"
                        )
                        SavedPrefManager.saveStringPreferences(
                            mContext,
                            SavedPrefManager.IMAGE_TWO,
                            "image_two"
                        )
                        SavedPrefManager.saveStringPreferences(
                            mContext,
                            SavedPrefManager.IMAGE_THREE,
                            "image_three"
                        )
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

                override fun onApiErrorBody(response: String?, apiName: String?) {
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

        val apiRequest = Api_Request_AddPostpackage()
        val location = Location(
            "Point",
            arrayListOf(
                SavedPrefManager.getLatitudeLocation(),
                SavedPrefManager.getLongitudeLocation()
            )
        )
        apiRequest.mediaType = imageType.toUpperCase()
        apiRequest.description = description.text.toString()
        apiRequest.categoryId = SavedPrefManager.getStringPreferences(activity, AppConst.POST_CATEGORY_ID)
        apiRequest.videoLink = videoLink
        apiRequest.address = locality
        apiRequest.imageLinks = responseImageList
        apiRequest.location = location
        try {
            serviceManager.userAddPost(callBack, apiRequest)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


    private fun setSpinnerAdapter(spinnerData: ArrayList<String?>) {
        try {
            val adapter: ArrayAdapter<*> = ArrayAdapter<Any?>(
                activity!!,
                android.R.layout.simple_list_item_1,
                spinnerData as List<Any?>
            )

            spin.adapter = adapter
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
    }


//    fun StringToBitMap(image: String?): Bitmap? {
//        return try {
//            val encodeByte: ByteArray = Base64.decode(image, Base64.DEFAULT)
//            val inputStream: InputStream = ByteArrayInputStream(encodeByte)
//            BitmapFactory.decodeStream(inputStream)
//        } catch (e: Exception) {
//            e.message
//            null
//        }
//    }


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

                setImageAndVideos()
                Toast.makeText(
                    activity,
                    "Cancelled image capture!", Toast.LENGTH_SHORT
                )
                    .show();
                bottomSheetDialog.dismiss()
            } else {
                try {
                    if (requestCode == GALLERY) {
                        if (data != null && data!!.clipData == null) {
                            try {
                                image = data.data!!
                                val cr: ContentResolver = mContext.getContentResolver()
                                val mime = cr.getType(image)
                                var videoType = mime!!.substring(0, 5)

                                val path = getPathFromURI(image)
                                if (path != null) {
                                    imageFile = File(path)
                                }
                                if (videoType == "video") {
                                    if(count==0)
                                    {
                                        videoCount++
                                        if (videoType == "video" && videoCount > 1)
                                        {
                                            setImageAndVideos()
                                            Toast.makeText(
                                                mContext,
                                                "You not select more than 1 video!!",
                                                Toast.LENGTH_LONG
                                            ).show()

                                        } else {
                                            if (galleryData1.visibility == View.GONE && count == 0) {
                                                addImageOne.visibility = View.GONE
                                                addImageTwo.visibility = View.GONE
                                                addImageThree.visibility = View.GONE
                                                galleryData1.visibility = View.VISIBLE
                                                Glide.with(mContext).load(imageFile).into(galleryData1)
                                                bottomSheetDialog.dismiss()

                                                SavedPrefManager.saveStringPreferences(
                                                    mContext,
                                                    SavedPrefManager.IMAGE_ONE,
                                                    imageFile.toString()
                                                )
                                                count++
                                            } else if (galleryData2.visibility == View.GONE && count == 1) {
                                                addImageTwo.visibility = View.GONE
                                                addImageThree.visibility = View.VISIBLE
                                                galleryData2.visibility = View.VISIBLE
                                                Glide.with(mContext).load(imageFile).into(galleryData2)
                                                bottomSheetDialog.dismiss()

                                                SavedPrefManager.saveStringPreferences(
                                                    mContext,
                                                    SavedPrefManager.IMAGE_TWO,
                                                    imageFile.toString()
                                                )

                                                var imageOne =
                                                    SavedPrefManager.getStringPreferences(
                                                        mContext,
                                                        SavedPrefManager.IMAGE_ONE
                                                    )
                                                var image = File(imageOne)
                                                galleryData1.visibility = View.VISIBLE
                                                Glide.with(mContext).load(image).into(galleryData1)
                                                count++

                                            } else if (galleryData3.visibility == View.GONE && count == 2) {
                                                addImageThree.visibility = View.GONE
                                                galleryData3.visibility = View.VISIBLE
                                                Glide.with(mContext).load(imageFile).into(galleryData3)
                                                bottomSheetDialog.dismiss()

                                                SavedPrefManager.saveStringPreferences(
                                                    mContext,
                                                    SavedPrefManager.IMAGE_THREE,
                                                    imageFile.toString()
                                                )

                                                var imageTwo =
                                                    SavedPrefManager.getStringPreferences(
                                                        mContext,
                                                        SavedPrefManager.IMAGE_TWO
                                                    )
                                                var imageFTwo = File(imageTwo)
                                                galleryData2.visibility = View.VISIBLE
                                                Glide.with(mContext).load(imageFTwo).into(galleryData2)

                                                var imageOne =
                                                    SavedPrefManager.getStringPreferences(
                                                        mContext,
                                                        SavedPrefManager.IMAGE_ONE
                                                    )
                                                var image = File(imageOne)
                                                galleryData1.visibility = View.VISIBLE
                                                Glide.with(mContext).load(image).into(galleryData1)
                                                count++

                                            }

                                            var requestGalleryImageFile: RequestBody =
                                                RequestBody.create(
                                                    "video/*".toMediaTypeOrNull(),
                                                    imageFile
                                                )
                                            imageparts.add(
                                                MultipartBody.Part.createFormData(
                                                    "video",
                                                    imageFile.getName(),
                                                    requestGalleryImageFile
                                                )
                                            )
                                        }
                                    }
                                    else
                                    {
                                        Toast.makeText(
                                            activity,
                                            "You can select either images or video in one time...",
                                            Toast.LENGTH_LONG
                                        ).show()
                                    }

                                } else {

                                    multiPartImageSet()
                                    bottomSheetDialog.dismiss()
                                    var requestGalleryImageFile: RequestBody =
                                        RequestBody.create("image/*".toMediaTypeOrNull(), imageFile)
                                    imageparts.add(
                                        MultipartBody.Part.createFormData(
                                            "image",
                                            imageFile.getName(),
                                            requestGalleryImageFile
                                        )
                                    )
                                }

                            } catch (e: java.lang.Exception) {
                                e.printStackTrace()
                            }
                        }

                    } else if (requestCode == CAMERA) {
                        fileFlag = "single_image"
                        imageFile = File(imagePath)
                        uriImageList.add(Uri.fromFile(imageFile))

                        multiPartImageSet()

                        bottomSheetDialog.dismiss()
                        var requestGalleryImageFile: RequestBody =
                            RequestBody.create("image/*".toMediaTypeOrNull(), imageFile)
                        imageparts.add(
                            MultipartBody.Part.createFormData(
                                "image",
                                imageFile.getName(),
                                requestGalleryImageFile
                            )
                        )
                    }
                    else if(requestCode == CAPTURE_VIDEO_ACTIVITY_REQUEST_CODE) {
                        try {
                            imageUri = data?.data!!
                            bottomSheetDialog.dismiss()
                            processVideo(imageUri,bottomSheetDialog)
//                            val path = getPathFromURI(imageUri)
//                            if (compressVideo != null) {
////                                imageFile = File(path)
//                                imageFile = compressVideo
//                            }
//                            bitmap = ThumbnailUtils.createVideoThumbnail(
//                                imageFile.absolutePath,
//                                MediaStore.Video.Thumbnails.MINI_KIND
//                            )
//                            Glide.with(mContext).load(bitmap).into(galleryData1);
//                            galleryData1.visibility = View.VISIBLE
//                            addImageOne.visibility = View.GONE
//                            addImageTwo.visibility = View.GONE
//                            addImageThree.visibility = View.GONE
////                            galleryData1.setImageBitmap(bitmap)
//                            bottomSheetDialog.dismiss()
//
//
//                            var requestGalleryImageFile: RequestBody =
//                                RequestBody.create(
//                                    "video/*".toMediaTypeOrNull(),
//                                    imageFile
//                                )
//                            imageparts.add(
//                                MultipartBody.Part.createFormData(
//                                    "video",
//                                    imageFile.getName(),
//                                    requestGalleryImageFile
//                                )
//                            )
                        }catch (e : Exception) {
                            e.printStackTrace()
                        }
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        } catch (e: KotlinNullPointerException) {
            e.printStackTrace()
        }
    }

    private fun setImageAndVideos() {
        if (SavedPrefManager.getStringPreferences(mContext, SavedPrefManager.IMAGE_ONE) != null) {
//            addImageOne.visibility = View.VISIBLE
//            addImageOne.visibility = View.GONE
//            addImageOne.visibility = View.GONE
//            var imagef =
//                SavedPrefManager.getStringPreferences(mContext, SavedPrefManager.IMAGE_ONE)
//            var imageFOne = File(imagef)
//            galleryData1.visibility = View.VISIBLE
//            Glide.with(mContext).load(imageFOne).into(galleryData1)
        } else if (SavedPrefManager.getStringPreferences(
                mContext,
                SavedPrefManager.IMAGE_TWO
            ) != null
        ) {
            addImageOne.visibility = View.GONE
            addImageOne.visibility = View.VISIBLE
            addImageOne.visibility = View.GONE
//            var imageTwo =
//                SavedPrefManager.getStringPreferences(mContext, SavedPrefManager.IMAGE_TWO)
//            var imageFTwo = File(imageTwo)
//            galleryData2.visibility = View.VISIBLE
//            Glide.with(mContext).load(imageFTwo).into(galleryData2)
        } else if (SavedPrefManager.getStringPreferences(
                mContext,
                SavedPrefManager.IMAGE_THREE
            ) != null
        ) {
            addImageOne.visibility = View.GONE
            addImageOne.visibility = View.GONE
            addImageOne.visibility = View.VISIBLE
//            var imageThree = SavedPrefManager.getStringPreferences(
//                mContext,
//                SavedPrefManager.IMAGE_THREE
//            )
//            var imageFThree = File(imageThree)
//            galleryData3.visibility = View.VISIBLE
//            Glide.with(mContext).load(imageFThree).into(galleryData3)
        }
    }

    private fun multiPartImageSet() {
        if (count >= 1) {
            if (count >= 2) {
                if (count >= 3) {
                    Toast.makeText(
                        activity,
                        "Not select more than 3 photos",
                        Toast.LENGTH_SHORT
                    ).show()
                    //get
                    var imagef =
                        SavedPrefManager.getStringPreferences(mContext, SavedPrefManager.IMAGE_ONE)
                    var imageFOne = File(imagef)
                    galleryData1.visibility = View.VISIBLE
                    Glide.with(mContext).load(imageFOne).into(galleryData1)

                    var imageTwo =
                        SavedPrefManager.getStringPreferences(mContext, SavedPrefManager.IMAGE_TWO)
                    var imageFTwo = File(imageTwo)
                    galleryData2.visibility = View.VISIBLE
                    Glide.with(mContext).load(imageFTwo).into(galleryData2)

                    var imageThree = SavedPrefManager.getStringPreferences(
                        mContext,
                        SavedPrefManager.IMAGE_THREE
                    )
                    var imageFThree = File(imageThree)
                    galleryData3.visibility = View.VISIBLE
                    Glide.with(mContext).load(imageFThree).into(galleryData3)

                } else {
                    count = 3
                    addImageThree.visibility = View.GONE
                    galleryData3.visibility = View.VISIBLE
                    Glide.with(mContext).load(imageFile).into(galleryData3)
                    SavedPrefManager.saveStringPreferences(
                        mContext,
                        SavedPrefManager.IMAGE_THREE,
                        imageFile.toString()
                    )

//                                        get
                    var imageThree = SavedPrefManager.getStringPreferences(
                        mContext,
                        SavedPrefManager.IMAGE_THREE
                    )
                    var imageFThree = File(imageThree)
                    galleryData3.visibility = View.VISIBLE
                    Glide.with(mContext).load(imageFThree).into(galleryData3)
                    var imagef =
                        SavedPrefManager.getStringPreferences(mContext, SavedPrefManager.IMAGE_ONE)
                    var imageFOne = File(imagef)
                    galleryData1.visibility = View.VISIBLE
                    Glide.with(mContext).load(imageFOne).into(galleryData1)
                    var imageTwo =
                        SavedPrefManager.getStringPreferences(mContext, SavedPrefManager.IMAGE_TWO)
                    var imageFTwo = File(imageTwo)
                    galleryData2.visibility = View.VISIBLE
                    Glide.with(mContext).load(imageFTwo).into(galleryData2)

                }
            } else {
                count++
                addImageTwo.visibility = View.GONE
                addImageThree.visibility = View.VISIBLE
                galleryData2.visibility = View.VISIBLE
                Glide.with(mContext).load(imageFile).into(galleryData2)
                SavedPrefManager.saveStringPreferences(
                    mContext,
                    SavedPrefManager.IMAGE_TWO,
                    imageFile.toString()
                )

//                                    get
                var imageTwo =
                    SavedPrefManager.getStringPreferences(mContext, SavedPrefManager.IMAGE_TWO)
                var imageFTwo = File(imageTwo)
                galleryData2.visibility = View.VISIBLE
                Glide.with(mContext).load(imageFTwo).into(galleryData2)
                var imageOne =
                    SavedPrefManager.getStringPreferences(mContext, SavedPrefManager.IMAGE_ONE)
                var image = File(imageOne)
                galleryData1.visibility = View.VISIBLE
                Glide.with(mContext).load(image).into(galleryData1)

            }
        } else {
            count++
            addImageOne.visibility = View.GONE
            addImageTwo.visibility = View.VISIBLE
            galleryData1.visibility = View.VISIBLE
            Glide.with(mContext).load(imageFile).into(galleryData1)
            SavedPrefManager.saveStringPreferences(
                mContext,
                SavedPrefManager.IMAGE_ONE,
                imageFile.toString()
            )

//                                get
            var imageOne =
                SavedPrefManager.getStringPreferences(mContext, SavedPrefManager.IMAGE_ONE)
            var image = File(imageOne)
            galleryData1.visibility = View.VISIBLE
            Glide.with(mContext).load(image).into(galleryData1)


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

    private fun address() {
        val gcd = Geocoder(mContext, Locale.getDefault())
        var addresses: List<Address>? = null
        try {
            addresses = gcd.getFromLocation(latitude, longitude, 1)
            Log.d("FULL_LOCATION", addresses.toString())
        } catch (e: IOException) {
            e.printStackTrace()
        }
        if (addresses != null && addresses.size > 0) {
            try {
                var fullAddress = addresses.get(0).getAddressLine(0)
                val arrOfStr: List<String> = fullAddress.split(", ")
                locality = arrOfStr[1] + ", " + arrOfStr[2] + ", " + addresses[0].locality
//                locality = addresses[0].locality

            } catch (e: NullPointerException) {
                e.printStackTrace()
            }
            println("locationlist" + locality)
        }
    }

    private fun getImages() {
        var bottomsheet = bottomSheetDialog("addpost", this)
        bottomsheet.show(fragmentManager!!, "bottomsheet")
    }

    override fun clickListner(
        requestCode: Int,
        resultCode: Int,
        data: Intent?,
        bottomSheetDialog: bottomSheetDialog,
        imagePath: String
    ) {
        addPostData(requestCode, resultCode, data, bottomSheetDialog, imagePath)
    }

    private fun processVideo(uri: Uri?, bottomSheetDialog: bottomSheetDialog) {
        var streamableFile: File? = null
        uri?.let {
//            mainContents.visibility = View.VISIBLE
//            Glide.with(applicationContext).load(uri).into(videoImage)

            GlobalScope.launch {
                // run in background as it can take a long time if the video is big,
                // this implementation is not the best way to do it,
                // todo(abed): improve threading
                val job = async { getMediaPath(activity!!, uri) }
                val path = job.await()

                val desFile = saveVideoFile(path)

                streamableFile = saveVideoFile(path)

                playableVideoPath = if (streamableFile != null) streamableFile!!.path
                else path

                desFile?.let {
                    var time = 0L
                    VideoCompressor.start(
                        context = activity!!,
                        srcUri = uri,
                        // srcPath = path,
                        destPath = desFile.path,
                        streamableFile = streamableFile?.path,
                        listener = object : CompressionListener {

                            override fun onStart() {
                                progressBarPost.visibility = View.VISIBLE
                                progressBarPost.progress = 0
                            }
                            override fun onSuccess() {
//                                compressVideo = streamableFile!!
                                progressBarPost.visibility = View.GONE
                                val newSizeValue = streamableFile!!.length()

                                if (streamableFile != null) {
//                                imageFile = File(path)
                                    imageFile = streamableFile!!
//                                    Toast.makeText(mContext, "${getFileSize(newSizeValue)}", Toast.LENGTH_LONG).show()
                                }
                                bitmap = ThumbnailUtils.createVideoThumbnail(
                                    imageFile.absolutePath,
                                    MediaStore.Video.Thumbnails.MINI_KIND
                                )
                                Glide.with(mContext).load(bitmap).into(galleryData1);
                                galleryData1.visibility = View.VISIBLE
                                addImageOne.visibility = View.GONE
                                addImageTwo.visibility = View.GONE
                                addImageThree.visibility = View.GONE
//                            galleryData1.setImageBitmap(bitmap)
                                bottomSheetDialog.dismiss()


                                var requestGalleryImageFile: RequestBody =
                                    RequestBody.create(
                                        "video/*".toMediaTypeOrNull(),
                                        imageFile
                                    )
                                imageparts.add(
                                    MultipartBody.Part.createFormData(
                                        "video",
                                        imageFile.getName(),
                                        requestGalleryImageFile
                                    )
                                )
                            }
                            override fun onProgress(percent: Float) {

                            }
                            override fun onFailure(failureMessage: String) {
                                Toast.makeText(mContext,"failure", Toast.LENGTH_LONG).show()
                            }
                            override fun onCancelled() {
                            }
                        },
                        configureWith = Configuration(
                            quality = VideoQuality.HIGH,
                            frameRate = 24,
                            isMinBitrateCheckEnabled = true,
                        )
                    )
                }
            }
        }
    }

    private fun saveVideoFile(filePath: String?): File? {
        filePath?.let {
            val videoFile = File(filePath)
            val videoFileName = "${System.currentTimeMillis()}_${videoFile.name}"
            val folderName = Environment.DIRECTORY_MOVIES
            if (Build.VERSION.SDK_INT >= 30) {

                val values = ContentValues().apply {

                    put(
                        MediaStore.Images.Media.DISPLAY_NAME,
                        videoFileName
                    )
                    put(MediaStore.Images.Media.MIME_TYPE, "video/mp4")
                    put(MediaStore.Images.Media.RELATIVE_PATH, folderName)
                    put(MediaStore.Images.Media.IS_PENDING, 1)
                }

                val collection =
                    MediaStore.Video.Media.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY)

                val fileUri = activity!!.contentResolver.insert(collection, values)

                fileUri?.let {
                    activity!!.contentResolver.openFileDescriptor(fileUri, "rw")
                        .use { descriptor ->
                            descriptor?.let {
                                FileOutputStream(descriptor.fileDescriptor).use { out ->
                                    FileInputStream(videoFile).use { inputStream ->
                                        val buf = ByteArray(4096)
                                        while (true) {
                                            val sz = inputStream.read(buf)
                                            if (sz <= 0) break
                                            out.write(buf, 0, sz)
                                        }
                                    }
                                }
                            }
                        }

                    values.clear()
                    values.put(MediaStore.Video.Media.IS_PENDING, 0)
                    activity!!.contentResolver.update(fileUri, values, null, null)

                    return File(getMediaPath(activity!!, fileUri))
                }
            } else {
                val downloadsPath =
                    activity!!.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS)
                val desFile = File(downloadsPath, videoFileName)

                if (desFile.exists())
                    desFile.delete()

                try {
                    desFile.createNewFile()
                } catch (e: IOException) {
                    e.printStackTrace()
                }

                return desFile
            }
        }
        return null
    }

}


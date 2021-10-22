package com.example.myapplication.Fragments

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.R
import com.example.myapplication.entity.ApiCallBack
import com.example.myapplication.entity.Request.Api_Request
import com.example.myapplication.entity.Request.Location
import com.example.myapplication.entity.Response.Responce
import com.example.myapplication.entity.Service_Base.ApiResponseListener
import com.example.myapplication.entity.Service_Base.ServiceManager
import com.example.myapplication.extension.androidextention
import com.example.myapplication.util.AppConst
import com.example.myapplication.util.SavedPrefManager
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.MultipartBody.Part.Companion.createFormData
import okhttp3.RequestBody
import okhttp3.ResponseBody
import java.io.ByteArrayInputStream
import java.io.File
import java.io.InputStream


class AddPostFragment : Fragment() {
    private lateinit var spin : Spinner
    private lateinit var galleryData1 : ImageView
    private lateinit var galleryData2 : ImageView
    private lateinit var galleryData3 : ImageView
    private lateinit var description : EditText
    private lateinit var postBackButton : ImageView
    private lateinit var spinDropDown : ImageView
    private lateinit var post : LinearLayout
    var HorizontalLayout: LinearLayoutManager? = null
    lateinit var serviceManager : ServiceManager
    lateinit var mContext : Context
    private var postDescriptionText = ""
    lateinit var imageData: ArrayList<Bitmap?>
    lateinit var imageData1: ArrayList<Uri?>
    var categoryItem: ArrayList<String?> = ArrayList()
    lateinit var callBack: ApiCallBack<Responce>
    lateinit var image: MultipartBody.Part


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view : View = inflater.inflate(R.layout.fragment_add_post, container, false)
        serviceManager = ServiceManager(activity)
        mContext = activity!!.applicationContext
        post=view.findViewById(R.id.post)
        galleryData1 = view.findViewById(R.id.image1)
        galleryData2 = view.findViewById(R.id.image2)
        galleryData3 = view.findViewById(R.id.image3)
        description = view.findViewById(R.id.post_description)
        postBackButton = view.findViewById(R.id.post_back_button)
        spinDropDown = view.findViewById(R.id.spinDropDown)
        spin = view.findViewById(R.id.spinner2)
        postDescriptionText = description.text.toString()
        imageData= arguments?.getParcelableArrayList<Bitmap>("BitmapImage") as ArrayList<Bitmap?>
//        imageData1= arguments?.getString("BitmapImage") as ArrayList<Uri?>
        if(imageData.size == 3) {
            SavedPrefManager.saveStringPreferences(activity, AppConst.IMAGEDATA, "true")
        }
        if(imageData.size == 1){
            galleryData1.setImageBitmap(imageData.get(0))

        }else if(imageData.size == 2) {
            galleryData1.setImageBitmap(imageData.get(0))
            galleryData2.setImageBitmap(imageData.get(1))
        } else if(imageData.size == 3) {
            galleryData1.setImageBitmap(imageData.get(0))
            galleryData2.setImageBitmap(imageData.get(1))
            galleryData3.setImageBitmap(imageData.get(2))
        }

        postBackButton.setOnClickListener{
            SavedPrefManager.saveStringPreferences(activity, AppConst.IMAGEDATA, "false")
            fragmentManager?.beginTransaction()?.replace(
                R.id.linear_layout,
                HomeFragment()
            )?.commit()
        }

        post.setOnClickListener{
            SavedPrefManager.saveStringPreferences(activity, AppConst.IMAGEDATA, "false")
            addPost()
            uploadMediaApi()
        }

        spinDropDown.setOnClickListener{
            categoryListApi()
        }
        return view
    }


    private fun categoryListApi() {
        androidextention.showProgressDialog(mContext)
        callBack =
            ApiCallBack<Responce>(object : ApiResponseListener<Responce> {
                override fun onApiSuccess(response: Responce, apiName: String?) {
                    androidextention.disMissProgressDialog(mContext)
                    if (response.responseCode == "200") {
                        for(i in 0 until response.result.categoryResult.size) {
                            categoryItem.add(response.result.categoryResult.get(i).categoryName)
                            SavedPrefManager.saveStringPreferences(activity,AppConst.POST_CATEGORY_ID,response.result.categoryResult.get(i)._id)
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
                    androidextention.disMissProgressDialog(mContext)
                    Toast.makeText(
                        activity,
                        "error response" + response.toString(),
                        Toast.LENGTH_LONG
                    ).show()
                }

                override fun onApiFailure(failureMessage: String?, apiName: String?) {
                    androidextention.disMissProgressDialog(mContext)
                    Toast.makeText(
                        activity,
                        "failure response:" + failureMessage,
                        Toast.LENGTH_LONG
                    ).show()
                }

            }, "CategoryList",mContext)

        try {
            serviceManager.getCategoryList(callBack)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun addPost() {
        androidextention.showProgressDialog(mContext)
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
                    androidextention.disMissProgressDialog(mContext)
                    Toast.makeText(
                        activity,
                        "error response" + response.toString(),
                        Toast.LENGTH_LONG
                    ).show()
                }

                override fun onApiFailure(failureMessage: String?, apiName: String?) {
                    androidextention.disMissProgressDialog(mContext)
                    Toast.makeText(
                        activity,
                        "failure response:" + failureMessage,
                        Toast.LENGTH_LONG
                    ).show()
                }

            }, "AddPost",mContext)

        val apiRequest = Api_Request()
        val location = Location("Point", arrayListOf(0.0,0.0))
        apiRequest.mediaType = "PHOTO"
        apiRequest.description = postDescriptionText
        apiRequest.categoryId = SavedPrefManager.getStringPreferences(activity,AppConst.POST_CATEGORY_ID)
        apiRequest.videoLink = "video_link"
        val imageList :ArrayList<String> = arrayListOf("https://res.cloudinary.com/mobiloitte-testing/image/upload/v1634815551/vtvhmo2gx9qhcn125and.jpg","https://res.cloudinary.com/mobiloitte-testing/image/upload/v1634815551/vtvhmo2gx9qhcn125and.jpg")
        apiRequest.imageLinks = imageList
        apiRequest.location = location

        try {
            serviceManager.userAddPost(callBack,apiRequest)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun uploadMediaApi() {
        androidextention.showProgressDialog(mContext)
        callBack =
            ApiCallBack<Responce>(object : ApiResponseListener<Responce> {
                override fun onApiSuccess(response: Responce, apiName: String?) {
                    androidextention.disMissProgressDialog(mContext)
                    if (response.responseCode == "200") {

                    } else {
                        Toast.makeText(
                            activity,
                            response.responseMessage,
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }

                override fun onApiErrorBody(response: ResponseBody?, apiName: String?) {
                    androidextention.disMissProgressDialog(mContext)
                    Toast.makeText(
                        activity,
                        "error response" + response.toString(),
                        Toast.LENGTH_LONG
                    ).show()
                }

                override fun onApiFailure(failureMessage: String?, apiName: String?) {
                    androidextention.disMissProgressDialog(mContext)
                    Toast.makeText(
                        activity,
                        "failure response:" + failureMessage,
                        Toast.LENGTH_LONG
                    ).show()
                }

            }, "UploadMedia",mContext)
        for(i in 0 until imageData1.size) {
            var imageUrl = imageData1.get(i).toString()
            var data1 = File(imageUrl)

            val data: RequestBody = RequestBody.create("image/*".toMediaTypeOrNull(), data1)
            image = createFormData("image", data1.getName(), data)
        }

        try {
            serviceManager.userUploadMedia(callBack,image)
        } catch (e: Exception) {
            e.printStackTrace()
        }


    }

    private fun setSpinnerAdapter(spinnerData : ArrayList<String?>){
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

}


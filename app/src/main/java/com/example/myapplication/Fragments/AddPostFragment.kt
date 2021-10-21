package com.example.myapplication.Fragments

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.entity.ApiCallBack
import com.example.myapplication.entity.Request.Api_Request
import com.example.myapplication.entity.Request.Location
import com.example.myapplication.entity.Request.SocialLinks
import com.example.myapplication.entity.Response.Responce
import com.example.myapplication.entity.Service_Base.ApiResponseListener
import com.example.myapplication.entity.Service_Base.ServiceManager
import com.example.myapplication.extension.androidextention
import okhttp3.ResponseBody
import java.io.ByteArrayInputStream
import java.io.InputStream


class AddPostFragment : Fragment() {
    private lateinit var spin : Spinner
    private lateinit var img : ImageView
    private lateinit var description : EditText
    private lateinit var postBackButton : ImageView
    private lateinit var post : LinearLayout
    var HorizontalLayout: LinearLayoutManager? = null
    lateinit var serviceManager : ServiceManager
    lateinit var mContext : Context
    private var postDescriptionText = ""


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view : View = inflater.inflate(R.layout.fragment_add_post, container, false)
        serviceManager = ServiceManager(activity)
        mContext = activity!!.applicationContext
        post=view.findViewById(R.id.post)
        img = view.findViewById(R.id.image1)
        description = view.findViewById(R.id.post_description)
        postBackButton = view.findViewById(R.id.post_back_button)
        postDescriptionText = description.text.toString()
        var imageData: Bitmap? = arguments?.getParcelable("BitmapImage")
        if(imageData!=null){
            img.setImageBitmap(imageData)
        }

        postBackButton.setOnClickListener{
            fragmentManager?.beginTransaction()?.replace(
                R.id.linear_layout,
                HomeFragment()
            )?.commit()
        }
        post.setOnClickListener{
            addPost()
        }
        spin = view.findViewById(R.id.spinner2)

        val objects = arrayOf<String?>(
            "Choose category","Weather","Crime","Traffic","WTF?","Aliens?"
        )

        val adapter: ArrayAdapter<*> = ArrayAdapter<Any?>(
            activity!!,
            android.R.layout.simple_list_item_1,
            objects
        )

        spin.adapter = adapter
//        spin.setOnItemSelectedListener()


        return view
    }

    private fun categoryListApi() {
        androidextention.showProgressDialog(mContext)
        val callBack: ApiCallBack<Responce> =
            ApiCallBack<Responce>(object : ApiResponseListener<Responce> {
                override fun onApiSuccess(response: Responce, apiName: String?) {
                    androidextention.disMissProgressDialog(mContext)
                    if (response.responseCode == "200") {
                        Toast.makeText(
                            activity,
                            response.responseMessage,
                            Toast.LENGTH_LONG
                        ).show()

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

            }, "EditProfile",mContext)

        try {
            serviceManager.getCategoryList(callBack)
        } catch (e: Exception) {
            e.printStackTrace()
        }


    }

    private fun addPost() {
        androidextention.showProgressDialog(mContext)
        val callBack: ApiCallBack<Responce> =
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

            }, "EditProfile",mContext)

        val apiRequest = Api_Request()
        val location = Location("Point", arrayListOf(0.0,0.0))
        apiRequest.mediaType = "PHOTO"
        apiRequest.description = postDescriptionText
        apiRequest.categoryId = "61714e421323665e341bf3d3"
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


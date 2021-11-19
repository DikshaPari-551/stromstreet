package com.example.myapplication.Fragments

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.app.ActivityCompat
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.Activities.PostActivity
import com.example.myapplication.Adaptor.HomeAdaptor
import com.example.myapplication.Exoplayer
import com.example.myapplication.LoginActivity
import com.example.myapplication.R
import com.example.myapplication.customclickListner.CustomClickListner2
import com.example.myapplication.entity.ApiCallBack
import com.example.myapplication.entity.Request.Api_Request
import com.example.myapplication.entity.Response.Docss
import com.example.myapplication.entity.Response.LocalActivityResponse
import com.example.myapplication.entity.Service_Base.ApiResponseListener
import com.example.myapplication.entity.Service_Base.ServiceManager
import com.example.myapplication.extension.androidextention
import com.example.myapplication.util.SavedPrefManager
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import okhttp3.ResponseBody
import java.io.IOException
import java.util.*
import kotlin.collections.ArrayList

class HomeFragment : Fragment(), ApiResponseListener<LocalActivityResponse>, CustomClickListner2 {
    lateinit var mContext: Context
    private val LOCATION_PERMISSION_REQ_CODE = 1000;
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private var latitude: Double? = null
    private var longitude: Double? = null
    lateinit var man: LinearLayout
    lateinit var recycler_view2: RecyclerView
    lateinit var localpost: TextView
    lateinit var followingPost: TextView
    lateinit var userHome: LinearLayout
    lateinit var backArrowHome: ImageView
    lateinit var adaptor: HomeAdaptor
    lateinit var USERID: String
    lateinit var home_text: TextView
    lateinit var recycler_view1: RecyclerView
    lateinit var filter: LinearLayout
    lateinit var searchText: EditText
    lateinit var goButton: LinearLayout
    lateinit var progress_bar: ProgressBar
    lateinit var internetConnection: LinearLayout
    lateinit var nestedScrollView: NestedScrollView
    var list = ArrayList<Docss>()
    var getSearchText = ""
    var catId: String = ""
    var locality: String = ""
    var maxDis: Int = 0
    var page: Int = 1
    var pages: Int = 0
    var limit : Int = 10
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//        fusedLocationClient =
//            LocationServices.getFusedLocationProviderClient(mContext as FragmentActivity)

        // Inflate the layout for this fragment
        var v = inflater.inflate(R.layout.fragment_home, container, false)
        mContext = activity!!

        recycler_view1 = v.findViewById(R.id.recycler_view1)
        home_text = v.findViewById(R.id.home_text)
        localpost = v.findViewById(R.id.text_local_post)
        followingPost = v.findViewById(R.id.text_following_post)
        userHome = v.findViewById(R.id.user_home)
        backArrowHome = v.findViewById(R.id.back_arrow_home)
        man = v.findViewById(R.id.user_home)
        searchText = v.findViewById(R.id.search_text)
        internetConnection = v.findViewById(R.id.no_wifi)
        goButton = v.findViewById(R.id.go)
        progress_bar = v.findViewById(R.id.progress_bar)
        nestedScrollView = v.findViewById(R.id.nestedScrollView)
        filter = v.findViewById(R.id.filter)
        locationpermission()
        try {
            latitude = SavedPrefManager.getLatitudeLocation()!!
            longitude = SavedPrefManager.getLongitudeLocation()!!
            catId = arguments?.getString("CAT_ID")!!
            maxDis = arguments?.getInt("MAX_DIS")!!
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
        goButton.setOnClickListener {
            getSearchText = searchText.text.toString()
            getLocalActivityApi()
        }




        man.setOnClickListener {
            if ((SavedPrefManager.getStringPreferences(activity, SavedPrefManager.KEY_IS_LOGIN)
                    .equals("true"))
            ) {
                getFragmentManager()?.beginTransaction()?.replace(
                    R.id.linear_layout,
                    ProfileFragment()
                )
                    ?.commit()
            } else {
                val i = Intent(activity, LoginActivity::class.java)
                startActivity(i)
            }
        }

        localpost.setOnClickListener {
//            followingPost.setTextColor(resources.getColor(R.color.white))
//            home_text.setText("Local Activity")
//            localpost.setTextColor(resources.getColor(R.color.orange))
//            filter.visibility = View.GONE
//            userHome.visibility = View.GONE
//            backArrowHome.visibility = View.VISIBLE
            fragmentManager?.beginTransaction()?.replace(R.id.linear_layout, HomeFragment())
                ?.commit()

        }
        followingPost.setOnClickListener {
            if (SavedPrefManager.getStringPreferences(mContext, SavedPrefManager.KEY_IS_LOGIN)
                    .equals("true")
            ) {
//                followingPost.setTextColor(resources.getColor(R.color.orange))
//                home_text.setText("Following Activity")
//                localpost.setTextColor(resources.getColor(R.color.white))
//                filter.visibility = View.GONE
//                userHome.visibility = View.GONE
//                backArrowHome.visibility = View.VISIBLE
                fragmentManager?.beginTransaction()
                    ?.replace(R.id.linear_layout, FollowingActivityFragment())
                    ?.commit()
            } else {
                val i = Intent(mContext, LoginActivity::class.java)
                startActivity(i)
            }

        }


        backArrowHome.setOnClickListener {
            fragmentManager?.beginTransaction()?.replace(R.id.linear_layout, HomeFragment())
                ?.commit()
        }

        filter.setOnClickListener {
            getFragmentManager()?.beginTransaction()?.replace(
                R.id.linear_layout,
                secondFragment("home")
            )
                ?.commit()
        }

        nestedScrollView.setOnScrollChangeListener(object :  NestedScrollView.OnScrollChangeListener{
            override fun onScrollChange(
                v: NestedScrollView?,scrollX: Int,scrollY: Int,oldScrollX: Int,oldScrollY: Int) {
                if(scrollY == v!!.getChildAt(0).measuredHeight - v.measuredHeight){
//                    val lastVisibleItemPosition: Int = layoutManager.findLastVisibleItemPosition()

                    page++
                    progress_bar.visibility=View.VISIBLE
                    if(page > pages) {
                        progress_bar.visibility=View.GONE
                    } else {
                        getLocalActivityApi()
                    }
                }
            }
        })

        return v
    }

    private fun address() {
        val gcd = Geocoder(mContext, Locale.getDefault())
        var addresses: List<Address>? = null
        try {
            addresses = gcd.getFromLocation(latitude!!, longitude!!, 1)
        } catch (e: IOException) {
            e.printStackTrace()
        }
        if (addresses != null && addresses.size > 0) {
            try {
                locality = addresses[0].getLocality()
//
            } catch (e: NullPointerException) {
                e.printStackTrace()
            }
            println("locationlist" + locality)
        }
    }

    private fun getLocalActivityApi() {
        if (androidextention.isOnline(mContext)) {
            androidextention.showProgressDialog(mContext)
            val serviceManager = ServiceManager(mContext)
            val callBack: ApiCallBack<LocalActivityResponse> =
                ApiCallBack<LocalActivityResponse>(this, "LocalActivity", mContext)

            val apiRequest = Api_Request()
            apiRequest.categoryId = catId
            apiRequest.search = getSearchText

//            try {
                if (catId != null && !catId.equals("")) {

                    serviceManager.getLocalActivity(callBack, latitude, longitude, apiRequest,page.toString(),limit.toString())

                } else if (getSearchText != null && !getSearchText.equals("")) {

                    serviceManager.getLocalActivity(callBack, latitude, longitude, apiRequest,page.toString(),limit.toString())

                } else {

                    serviceManager.getLocalActivity(callBack, latitude, longitude, apiRequest,page.toString(),limit.toString())

                }
//            } catch (e: Exception) {
//                e.printStackTrace()
//            }
        } else {
            internetConnection.visibility = View.VISIBLE
        }
    }

    override fun onApiSuccess(response: LocalActivityResponse, apiName: String?) {
        progress_bar.visibility=View.GONE
        androidextention.disMissProgressDialog(activity)
        pages = response.result.pages

        list.addAll(response.result.docs)
        setAdapter(list)
//        Toast.makeText(mContext, "Success", Toast.LENGTH_LONG).show();
    }

    override fun onApiErrorBody(response: ResponseBody?, apiName: String?) {
        androidextention.disMissProgressDialog(activity)
        progress_bar.visibility=View.GONE

        Toast.makeText(activity, "Data Not Found", Toast.LENGTH_LONG).show()
    }

    override fun onApiFailure(failureMessage: String?, apiName: String?) {
        androidextention.disMissProgressDialog(activity)
        progress_bar.visibility=View.GONE

        Toast.makeText(activity, "Something want wrong", Toast.LENGTH_LONG).show()
    }

    fun setAdapter(list: ArrayList<Docss>) {

        adaptor = this?.let { HomeAdaptor(it, list, this) }!!
        val layoutManager = GridLayoutManager(activity, 2)
        recycler_view1?.layoutManager = layoutManager
        recycler_view1?.adapter = adaptor

//        recycler_view1.scrollToPosition(0)
//         adaptor.notifyDataSetChanged()


//        getData(page,limit)
    }




    override fun customClick(value: Docss, type: String) {
//        USERID =   "61711c7ec473b124b7369219"
        USERID = value._id
        var lat = value.location.coordinates
        if (type.equals("profile")) {
            if (value.mediaType.toLowerCase().equals("video")) {
                var intent = Intent(mContext, Exoplayer::class.java)
                SavedPrefManager.saveStringPreferences(mContext, SavedPrefManager._id, USERID)
                startActivity(intent)
            } else {
                var intent = Intent(mContext, PostActivity::class.java)
                SavedPrefManager.saveStringPreferences(mContext, SavedPrefManager._id, USERID)
                startActivity(intent)
            }
        }
    }

    private fun locationpermission() {
        // checking location permission
        if (ActivityCompat.checkSelfPermission(
                mContext,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // request permission
            ActivityCompat.requestPermissions(
                mContext as Activity,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                LOCATION_PERMISSION_REQ_CODE
            );
            return
        }
        var fusedLocationClient = LocationServices.getFusedLocationProviderClient(mContext)
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location ->
                // getting the last known or current location
                try {
                    latitude = location.latitude
                    longitude = location.longitude
                    SavedPrefManager.setLatitudeLocation(latitude!!)
                    SavedPrefManager.setLongitudeLocation(longitude!!)
                    getLocalActivityApi()
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
            .addOnFailureListener {
                Toast.makeText(
                    mContext, "Failed on getting current location",
                    Toast.LENGTH_SHORT
                ).show()
            }
    }
}
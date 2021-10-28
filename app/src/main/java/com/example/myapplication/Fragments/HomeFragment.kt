package com.example.myapplication.Fragments

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.app.ActivityCompat
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.Activities.PostActivity
import com.example.myapplication.Adaptor.HomeAdaptor
import com.example.myapplication.LoginActivity
import com.example.myapplication.R
import com.example.myapplication.customclickListner.CustomClickListner
import com.example.myapplication.customclickListner.CustomClickListner2
import com.example.myapplication.entity.ApiCallBack
import com.example.myapplication.entity.Request.Api_Request
import com.example.myapplication.entity.Response.Docs

import com.example.myapplication.entity.Response.Docss
import com.example.myapplication.entity.Response.LocalActivityResponse
import com.example.myapplication.entity.Service_Base.ApiResponseListener
import com.example.myapplication.entity.Service_Base.ServiceManager
import com.example.myapplication.extension.androidextention
import com.example.myapplication.util.SavedPrefManager
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import okhttp3.ResponseBody

class HomeFragment : Fragment(), ApiResponseListener<LocalActivityResponse> , CustomClickListner2 {
    lateinit var mContext: Context
    private val LOCATION_PERMISSION_REQ_CODE = 1000;
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private var latitude: Double = 0.0
    private var longitude: Double = 0.0
    lateinit var man: ImageView
//    var weather: List<String> = listOf("Weather", "Crime", "Weater", "Crime", "Weather")
//    var okhla: List<String> =
//        listOf("Okhla phase1", "Okhla phase2", "Okhla phase1", "Okhla phase2", "Okhla phase1")
//    var event: List<String> = listOf("Event", "Traffic", "Event", "Traffic", "Event")
//    var lajpat: List<String> =
//        listOf("Lajpat Nagar", "Okhla Saket", "Lajpat Nagar", "Saket", "Lajpat Nagar")
    lateinit var recycler_view2: RecyclerView
    lateinit var localpost: TextView
    lateinit var followingPost: TextView
    lateinit var userHome: ImageView
    lateinit var backArrowHome: ImageView
    lateinit var adaptor: HomeAdaptor
    lateinit var USERID: String


    lateinit var home_text: TextView
    lateinit var recycler_view1: RecyclerView
    lateinit var filter: ImageView
    lateinit var searchText: EditText
    lateinit var goButton: LinearLayout
    var getSearchText = ""
 var catId: String =""
    var maxDis: Int = 0
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mContext = activity!!
        fusedLocationClient =
            LocationServices.getFusedLocationProviderClient(mContext as FragmentActivity)

        // Inflate the layout for this fragment
        var v = inflater.inflate(R.layout.fragment_home, container, false)
        recycler_view1 = v.findViewById(R.id.recycler_view1)
        home_text = v.findViewById(R.id.home_text)
        localpost = v.findViewById(R.id.text_local_post)
        followingPost = v.findViewById(R.id.text_following_post)
        userHome = v.findViewById(R.id.user_home)
        backArrowHome = v.findViewById(R.id.back_arrow_home)
        man = v.findViewById(R.id.user_home)
        searchText = v.findViewById(R.id.search_text)
        goButton = v.findViewById(R.id.go)
        getSearchText = searchText.text.toString()
        try {
            catId = arguments?.getString("CAT_ID")!!
            maxDis = arguments?.getInt("MAX_DIS")!!
        } catch(e : java.lang.Exception) {
            e.printStackTrace()
        }
        locationpermission()
        getLocalActivityApi()
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


        followingPost.setOnClickListener {
            followingPost.setTextColor(resources.getColor(R.color.orange))
            home_text.setText("Following Activity")
            localpost.setTextColor(resources.getColor(R.color.white))
            filter.visibility = View.GONE
            userHome.visibility = View.GONE
            backArrowHome.visibility = View.VISIBLE

        }
        localpost.setOnClickListener {
            followingPost.setTextColor(resources.getColor(R.color.white))
            home_text.setText("Local Activity")
            localpost.setTextColor(resources.getColor(R.color.orange))
            filter.visibility = View.GONE
            userHome.visibility = View.GONE
            backArrowHome.visibility = View.VISIBLE


        }

        backArrowHome.setOnClickListener {
            fragmentManager?.beginTransaction()?.replace(R.id.linear_layout, HomeFragment())
                ?.commit()
        }

        filter = v.findViewById(R.id.filter)
        filter.setOnClickListener {
            getFragmentManager()?.beginTransaction()?.replace(
                R.id.linear_layout,
                secondFragment()
            )
                ?.commit()

        }
//        var adaptor = activity?.let {
//            HomeAdaptor(
//                weather,
//                okhla,
//                event,
//                lajpat,
//                it
//            )
//        }



//        val layoutManager = GridLayoutManager(activity, 2)
//
//        recycler_view1.layoutManager = layoutManager
//        recycler_view1.adapter = adaptor

        return v
    }

    private fun getLocalActivityApi() {
        if (androidextention.isOnline(mContext)) {
            androidextention.showProgressDialog(mContext)
            val serviceManager = ServiceManager(mContext)
            val callBack: ApiCallBack<LocalActivityResponse> =
                ApiCallBack<LocalActivityResponse>(this, "LocalActivity", mContext)

            val apiRequest = Api_Request()
            apiRequest.categoryId = catId
            apiRequest.search = searchText.text.toString()
            try {
                if(catId != null && !catId.equals("")) {
//                serviceManager.getLocalActivity(callBack,latitude,longitude,apiRequest)
                serviceManager.getLocalActivity(callBack,28.525377,77.280106,apiRequest)
                }
                else if(getSearchText != null) {
                    serviceManager.getLocalActivity(callBack,latitude,longitude,apiRequest)
                }
                else {
                    serviceManager.getLocalActivity(callBack,latitude,longitude,null)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    override fun onApiSuccess(response: LocalActivityResponse, apiName: String?) {
        androidextention.disMissProgressDialog(activity)

        var list = ArrayList<Docss>()
        list.addAll(response.result.docs)
        setAdapter(list)

        Toast.makeText(mContext, "Success", Toast.LENGTH_LONG).show();
    }

    override fun onApiErrorBody(response: ResponseBody?, apiName: String?) {
        Toast.makeText(activity, "Data Not Found", Toast.LENGTH_LONG).show()
    }

    override fun onApiFailure(failureMessage: String?, apiName: String?) {
        Toast.makeText(activity, "fail", Toast.LENGTH_LONG).show()
    }

    fun setAdapter(list: ArrayList<Docss>) {

        adaptor = this?.let { HomeAdaptor(it, list,this) }!!
        val layoutManager = GridLayoutManager(activity,2)
        recycler_view1?.layoutManager = layoutManager
        recycler_view1?.adapter = adaptor
    }


    private fun locationpermission() {
        // checking location permission
        if (ActivityCompat.checkSelfPermission(
                mContext,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // request permission
            ActivityCompat.requestPermissions(
                mContext as Activity,
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                LOCATION_PERMISSION_REQ_CODE
            );
            return
        }
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location ->
                // getting the last known or current location
                try {
                    latitude = location.latitude
                    longitude = location.longitude
                } catch(e : Exception) {
                    e.printStackTrace()
                }
                           }
            .addOnFailureListener {
                Toast.makeText(mContext, "Failed on getting current location",
                    Toast.LENGTH_SHORT).show()
            }
    }

    override fun customClick(value: Docss, type: String)
    {
//        USERID =   "61711c7ec473b124b7369219"
        USERID =   value._id

        if (type.equals("profile")){
            var intent = Intent(mContext, PostActivity::class.java)
            SavedPrefManager.saveStringPreferences(mContext, SavedPrefManager._id,USERID)

                startActivity(intent)
        }

    }



}
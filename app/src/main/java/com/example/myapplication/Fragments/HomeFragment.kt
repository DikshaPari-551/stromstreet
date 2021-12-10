package com.example.myapplication.Fragments

import android.Manifest
import android.R.attr
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.app.ActivityCompat
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.myapplication.Activities.NoInternetActivity
import com.example.myapplication.Activities.NotificationActivity
import com.example.myapplication.Activities.PostActivity
import com.example.myapplication.Activities.UserProfile
import com.example.myapplication.Adaptor.HomeAdaptor
import com.example.myapplication.Exoplayer
import com.example.myapplication.LoginActivity
import com.example.myapplication.R
import com.example.myapplication.customclickListner.CustomClickListnerdelete
import com.example.myapplication.customclickListner.ScrollListener
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
import java.util.*
import kotlin.collections.ArrayList


class HomeFragment : Fragment(), ApiResponseListener<LocalActivityResponse>,
    CustomClickListnerdelete,
    ScrollListener {
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
    lateinit var notificatio: RelativeLayout
    lateinit var nestedScrollView: NestedScrollView
    lateinit var totalnotif: TextView
    lateinit var swipeRefresh: SwipeRefreshLayout
    var progress:Boolean=true
    var list = ArrayList<Docss>()
    var getSearchText = ""
    var catId: ArrayList<String>? = null
    var locality: String = ""
    var result: String =""

    var maxDis: Int = 0
    var page: Int = 1
    var pages: Int = 0
    var limit: Int = 10
    var searchFlag = false
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
        notificatio = v.findViewById(R.id.notificatio_icon)
        totalnotif = v.findViewById(R.id.totalNotification)
        swipeRefresh = v.findViewById(R.id.swipeRefresh)
        locationpermission()
//        totalnotif.visibility = View.GONE

        try {
            latitude = SavedPrefManager.getLatitudeLocation()!!
            longitude = SavedPrefManager.getLongitudeLocation()!!
            catId = (arguments?.getSerializable("CAT_ID") as ArrayList<String>?)!!
            maxDis = arguments?.getInt("MAX_DIS")!!
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
        if ((SavedPrefManager.getStringPreferences(activity, SavedPrefManager.KEY_IS_LOGIN)
                .equals("true"))
        ) {
            notificationCountapi()

        }
//        else{
//            totalnotif.visibility = View.GONE
//        }


        goButton.setOnClickListener {
            if (!searchText.text.toString().equals("") && searchText.text.toString() != null){
                list.clear()
                searchFlag = true
                getSearchText = searchText.text.toString()
                getLocalActivityApi()
            }

        }

        notificatio.setOnClickListener {
            if ((SavedPrefManager.getStringPreferences(activity, SavedPrefManager.KEY_IS_LOGIN)
                    .equals("true"))
            ) {
                val intent = Intent(mContext, NotificationActivity::class.java)
                startActivity(intent)
            } else {
                val i = Intent(mContext, LoginActivity::class.java)
                startActivity(i)
            }

        }

        swipeRefresh.setOnRefreshListener {
            refresh()
            swipeRefresh.isRefreshing = false
        }



        man.setOnClickListener {
            if ((SavedPrefManager.getStringPreferences(activity, SavedPrefManager.KEY_IS_LOGIN)
                    .equals("true"))
            ) {
                if (androidextention.isOnline(mContext)) {
                    getFragmentManager()?.beginTransaction()?.replace(
                        R.id.linear_layout,
                        ProfileFragment()
                    )
                        ?.commit()
                } else {
                    val intent = Intent(mContext, NoInternetActivity::class.java)
                    startActivity(intent)
                }
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

        nestedScrollView.setOnScrollChangeListener(object :
            NestedScrollView.OnScrollChangeListener {
            override fun onScrollChange(
                v: NestedScrollView?, scrollX: Int, scrollY: Int, oldScrollX: Int, oldScrollY: Int
            ) {
                if (scrollY == v!!.getChildAt(0).measuredHeight - v.measuredHeight) {
//                    val lastVisibleItemPosition: Int = layoutManager.findLastVisibleItemPosition()

                    page++
                    progress_bar.visibility = View.VISIBLE
                    if (page > pages) {
                        progress_bar.visibility = View.GONE
                    } else {
                        getLocalActivityApi()
                        androidextention.disMissProgressDialog(activity)

                    }
                }
            }
        })

        searchText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) {
                if (p0!!.length == 0) {
                    list.clear()
                    getSearchText = ""
                    getLocalActivityApi()
                }
            }

        })
        return v
    }

    private fun notificationCountapi() {
        if (androidextention.isOnline(mContext)) {
            val serviceManager = ServiceManager(mContext)
            val callBack: ApiCallBack<LocalActivityResponse> =
                ApiCallBack<LocalActivityResponse>(this, "notificationCountapi", mContext)
            try {
                serviceManager.getNotificationcount(callBack)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

    }

    private fun getLocalActivityApi() {
        if(progress)
        {
            androidextention.showProgressDialog(activity)
        }
        searchFlag = false
        if (androidextention.isOnline(mContext)) {

            val serviceManager = ServiceManager(mContext)
            val callBack: ApiCallBack<LocalActivityResponse> =
                ApiCallBack<LocalActivityResponse>(this, "LocalActivity", mContext)

            val apiRequest = Api_Request()
            apiRequest.categoryId = catId
            apiRequest.search = getSearchText

//            try {
            if (catId != null && !catId!!.equals(null)) {
                serviceManager.getLocalActivity(callBack,latitude,longitude,apiRequest,page.toString(),limit.toString())

            } else if (getSearchText != null && !getSearchText.equals("")) {
                serviceManager.getLocalActivity(callBack, latitude, longitude, apiRequest, page.toString(), limit.toString()
                )
            } else {
                serviceManager.getLocalActivity(callBack, latitude, longitude, apiRequest, page.toString(), limit.toString()
                )

            }
//            } catch (e: Exception) {
//                e.printStackTrace()
//            }
        } else {
            androidextention.disMissProgressDialog(mContext)
            totalnotif.visibility = View.GONE
            internetConnection.visibility = View.VISIBLE
        }
    }

    override fun onApiSuccess(response: LocalActivityResponse, apiName: String?) {
        internetConnection.visibility = View.GONE

        progress_bar.visibility = View.GONE
        androidextention.disMissProgressDialog(activity)
        pages = response.result.pages

        try {
            list.addAll(response.result.docs)
            setAdapter(list)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        if (apiName.equals("notificationCountapi")) {
            totalnotif.setText(response.result.notificationCount.toString())
            if (response.result.notificationCount == 0){
                totalnotif.visibility = View.GONE
            }else{
                totalnotif.visibility = View.VISIBLE
            }
        }
//        Toast.makeText(mContext, "Success", Toast.LENGTH_LONG).show();
    }

    override fun onApiErrorBody(response: String?, apiName: String?) {
        androidextention.disMissProgressDialog(activity)
        progress_bar.visibility = View.GONE
        if (apiName.equals("LocalActivity")){
            Toast.makeText(activity, "Data Not Found", Toast.LENGTH_LONG).show()
        }
        else{

        }

    }

    override fun onApiFailure(failureMessage: String?, apiName: String?) {
        androidextention.disMissProgressDialog(activity)
        progress_bar.visibility = View.GONE

        Toast.makeText(activity, "Something want wrong", Toast.LENGTH_LONG).show()
    }

    fun setAdapter(list: ArrayList<Docss>) {

        adaptor = this?.let { HomeAdaptor(it, list, this) }!!
        val layoutManager = GridLayoutManager(activity, 2)
        recycler_view1?.layoutManager = layoutManager
        recycler_view1?.adapter = adaptor

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

    override fun myScrollListener() {
    }

    override fun onResume() {
        super.onResume()
        if ((SavedPrefManager.getStringPreferences(activity, SavedPrefManager.KEY_IS_LOGIN)
                .equals("true"))
        ) {
            notificationCountapi()

        }
    }
    fun refresh(){
        progress=false
        page = 1
        list.clear()
        getLocalActivityApi()
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1){
            if (resultCode == Activity.RESULT_OK) {
                result = data!!.getStringExtra("result")
                System.out.println("postposition"+result)
                list!!.removeAt(result.toInt());
                adaptor.notifyItemRemoved(result.toInt());
                adaptor.notifyItemRangeChanged(result.toInt(), list.size)
            }
        }
    }

    override fun customClick(value: Docss, type: String, i: Int) {
        USERID = value._id
        var lat = value.location.coordinates
        var otheruserid = value.userId
        if(androidextention.isOnline(mContext)) {
            internetConnection.visibility = View.GONE

            if (type.equals("profile")) {
                if (value.mediaType.toLowerCase().equals("video")) {
                    SavedPrefManager.saveStringPreferences(mContext, SavedPrefManager._id, USERID)
                    var intent = Intent(mContext, Exoplayer::class.java)
                    intent.putExtra("postion", i.toString())
                    startActivityForResult(intent, 1)
                } else {
                    SavedPrefManager.saveStringPreferences(mContext, SavedPrefManager._id, USERID)
                    var intent = Intent(mContext, PostActivity::class.java)
                    intent.putExtra("postion", i.toString())
                    startActivityForResult(intent, 1)
                }
            } else if (type.equals("userid")) {
                if ((SavedPrefManager.getStringPreferences(activity, SavedPrefManager.KEY_IS_LOGIN)
                        .equals("true"))
                ) {
                    SavedPrefManager.saveStringPreferences(
                        mContext,
                        SavedPrefManager.otherUserId,
                        otheruserid
                    )
                    var intent = Intent(mContext, UserProfile::class.java)
//            intent.putExtra("id",value._id)
                    startActivity(intent)
                }
            }
        }else {
            androidextention.disMissProgressDialog(mContext)
            Toast.makeText(mContext,"Please check your internet connection.", Toast.LENGTH_LONG).show()
        }
    }
}
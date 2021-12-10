package com.example.myapplication.Fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.myapplication.Activities.PostActivity
import com.example.myapplication.Activities.UserProfile
import com.example.myapplication.Adaptor.FollowingListAdaptor
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
import okhttp3.ResponseBody

class FollowingActivityFragment : Fragment() , ApiResponseListener<LocalActivityResponse>,
    CustomClickListner2 {

    lateinit var mContext: Context
    lateinit var adaptor: FollowingListAdaptor
    lateinit var USERID: String
    lateinit var Go: LinearLayout
    lateinit var textLocalPostTrending: TextView
    lateinit var textFollowingPostTrending: TextView
    lateinit var searchText: EditText
    lateinit var recycler_view2: RecyclerView
    lateinit var trending_post_text: TextView
    lateinit var trandingBackButton: ImageView
    lateinit var filter: LinearLayout
    lateinit var userTrendingImg: LinearLayout
    lateinit var progress_bar: ProgressBar
    lateinit var internetConnection: LinearLayout
    lateinit var nestedScrollView: NestedScrollView
    lateinit var swipeRefresh: SwipeRefreshLayout
    var progress:Boolean=true
    private var latitude: Double = 0.0
    private var longitude: Double = 0.0
    var list = ArrayList<Docss>()
    var searchValue = ""
    var catId: ArrayList<String>? = null
    var maxDis: Int = 0
    var page: Int = 1
    var pages: Int = 0
    var limit : Int = 10
    var searchFlag = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mContext = activity!!

        // Inflate the layout for this fragment
        var v = inflater.inflate(R.layout.fragment_following_activity, container, false)
        Go = v.findViewById(R.id.go)
        searchText = v.findViewById(R.id.search_text)
        recycler_view2 = v.findViewById(R.id.recycler_view2)
        trending_post_text=v.findViewById(R.id.trending_post_text)
        trandingBackButton=v.findViewById(R.id.back_arrow_tranding)
        internetConnection = v.findViewById(R.id.no_wifi)
        userTrendingImg=v.findViewById(R.id.user_treanding_img)
        progress_bar = v.findViewById(R.id.progress_bar)
        nestedScrollView = v.findViewById(R.id.nestedScrollView)
        swipeRefresh = v.findViewById(R.id.swipeRefresh)
        textLocalPostTrending=v.findViewById(R.id.text_local_post_trending)
        try {
            latitude = SavedPrefManager.getLatitudeLocation()!!
            longitude = SavedPrefManager.getLongitudeLocation()!!
            catId = (arguments?.getSerializable("CAT_ID") as ArrayList<String>?)!!
            maxDis = arguments?.getInt("MAX_DIS")!!
        } catch(e : java.lang.Exception) {
            e.printStackTrace()
        }

        Go.setOnClickListener{
            if (!searchText.text.toString().equals("") && searchText.text.toString() != null){
            list.clear()
            searchValue = searchText.text.toString()
            getFollowingApi()
        }}
        getFollowingApi()

        trandingBackButton.setOnClickListener{
            fragmentManager?.beginTransaction()?.replace(R.id.linear_layout, TrendingFragment())?.commit()
        }
        swipeRefresh.setOnRefreshListener {
            refresh()
            swipeRefresh.isRefreshing = false
        }


        textLocalPostTrending.setOnClickListener{
            fragmentManager?.beginTransaction()?.replace(R.id.linear_layout, HomeFragment())
                ?.commit()
            textLocalPostTrending.setTextColor(resources.getColor(R.color.orange))
            trending_post_text.setText("Local Activity")
            textFollowingPostTrending.setTextColor(resources.getColor(R.color.white))
            trandingBackButton.visibility = View.VISIBLE
            userTrendingImg.visibility = View.GONE
            filter.visibility =View.GONE

        }

        textFollowingPostTrending=v.findViewById(R.id.text_following_post_trending)
        textFollowingPostTrending.setOnClickListener{
            fragmentManager?.beginTransaction()?.replace(R.id.linear_layout, FollowingActivityFragment())
                ?.commit()
            textFollowingPostTrending.setTextColor(resources.getColor(R.color.orange))
            trending_post_text.setText("Following Activity")
            textLocalPostTrending.setTextColor(resources.getColor(R.color.white))
            trandingBackButton.visibility = View.VISIBLE
            userTrendingImg.visibility = View.GONE
            filter.visibility =View.GONE
        }

        userTrendingImg.setOnClickListener{
            if(  SavedPrefManager.getStringPreferences(activity,  SavedPrefManager.KEY_IS_LOGIN).equals("true")){
                getFragmentManager()?.beginTransaction()?.replace(
                    R.id.linear_layout,
                    ProfileFragment()
                )
                    ?.commit()
            }else{
                val i = Intent(activity, LoginActivity::class.java)
                startActivity(i)
            }
        }
        filter=v.findViewById(R.id.filter)
        filter.setOnClickListener{
            getFragmentManager()?.beginTransaction()?.replace(
                R.id.linear_layout,
                secondFragment("following")
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
                        getFollowingApi()
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
                    searchValue = ""
                    getFollowingApi()
                }
            }

        })

        return v


    }

    private fun getFollowingApi() {
        searchFlag = false
        if(progress)
        {
            androidextention.showProgressDialog(mContext)
        }
        if (androidextention.isOnline(mContext)) {

            val serviceManager = ServiceManager(mContext)
            val callBack: ApiCallBack<LocalActivityResponse> =
                ApiCallBack<LocalActivityResponse>(this, "FollowingActivity", mContext)

            val apiRequest = Api_Request()
//            apiRequest.categoryId = catId
            apiRequest.maxDistance = maxDis.toString()
            apiRequest.search = searchValue

            try {
                if (catId != null && !catId!!.equals(null) || maxDis != null && maxDis > 0) {
                    serviceManager.getFollowingActivity(callBack,null,null, apiRequest,page.toString(),limit.toString())
                } else if (searchValue != null && !searchValue.equals("")) {
                    serviceManager.getFollowingActivity(callBack,null,null, apiRequest,page.toString(),limit.toString())
                } else {
                    serviceManager.getFollowingActivity(callBack,null, null, apiRequest,page.toString(),limit.toString())
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        } else {
            androidextention.disMissProgressDialog(mContext)
            internetConnection.visibility = View.VISIBLE
        }
    }

    override fun onApiSuccess(response: LocalActivityResponse, apiName: String?) {
        androidextention.disMissProgressDialog(activity)
        try {
            pages = response.result.pages
            list.addAll(response.result.docs)
            setAdapter(list)
        } catch(e : java.lang.Exception) {
            e.printStackTrace()
        }
    }

    override fun onApiErrorBody(response: String?, apiName: String?) {
        Toast.makeText(activity, "Data Not Found", Toast.LENGTH_LONG).show()
    }

    override fun onApiFailure(failureMessage: String?, apiName: String?) {
        Toast.makeText(activity, "Server not responding", Toast.LENGTH_LONG).show()
    }

    fun setAdapter(list: ArrayList<Docss>) {
        adaptor = this?.let { FollowingListAdaptor(it, list,this) }!!
        val layoutManager = GridLayoutManager(activity,2)
        recycler_view2?.layoutManager = layoutManager
        recycler_view2?.adapter = adaptor
        adaptor.notifyDataSetChanged()
    }

    override fun customClick(value: Docss, type: String)   {
        USERID = value._id
        var lat = value.location.coordinates
        var otheruserid = value.userId
        if(androidextention.isOnline(mContext)) {
            internetConnection.visibility = View.GONE

        if (type.equals("profile")) {
            if (value.mediaType.toLowerCase().equals("video")) {
                SavedPrefManager.saveStringPreferences(mContext, SavedPrefManager._id, USERID)
                var intent = Intent(mContext, Exoplayer::class.java)
                startActivityForResult(intent,1)
            }

            else {
                SavedPrefManager.saveStringPreferences(mContext, SavedPrefManager._id, USERID)
                var intent = Intent(mContext, PostActivity::class.java)
                startActivityForResult(intent,1)
            }
        }
        else if(type.equals("userid"))
        {
            if ((SavedPrefManager.getStringPreferences(activity, SavedPrefManager.KEY_IS_LOGIN)
                    .equals("true"))
            ) {
                SavedPrefManager.saveStringPreferences( mContext,SavedPrefManager.otherUserId,otheruserid)
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


    fun refresh(){
        progress=false
        page = 1
        list.clear()
        getFollowingApi()
    }
}



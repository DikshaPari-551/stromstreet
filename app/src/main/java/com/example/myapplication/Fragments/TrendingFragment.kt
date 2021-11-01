package com.example.myapplication.Fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.Activities.PostActivity
import com.example.myapplication.Adaptor.TrendingListAdaptor
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


class TrendingFragment : Fragment(), ApiResponseListener<LocalActivityResponse>,
    CustomClickListner2 {
    lateinit var mContext: Context
    lateinit var adaptor: TrendingListAdaptor
    lateinit var USERID: String
    lateinit var Go : LinearLayout
    lateinit var textLocalPostTrending:TextView
    lateinit var textFollowingPostTrending:TextView
    lateinit var recycler_view2: RecyclerView
    lateinit var trending_post_text:TextView
    lateinit var trandingBackButton: ImageView
    lateinit var filter: LinearLayout
    lateinit var userTrendingImg:ImageView
    lateinit var searchText: EditText
    lateinit var goButton: LinearLayout
    private var latitude: Double = 0.0
    private var longitude: Double = 0.0
    var searchValue = ""
    var catId: String = ""
    var maxDis: Int = 0
    //    var weather  : List<String> =listOf("Weather","Crime","Weater","Crime","Weather")
//    var okhla  : List<String> =listOf("Okhla phase1","Okhla phase2","Okhla phase1","Okhla phase2","Okhla phase1")
//    var event  : List<String> =listOf("Event","Traffic","Event","Traffic","Event")
//    var lajpat  : List<String> =listOf("Lajpat Nagar","Okhla Saket","Lajpat Nagar","Saket","Lajpat Nagar")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mContext = activity!!

        // Inflate the layout for this fragment
        var v= inflater.inflate(R.layout.fragment_trending, container, false)

        searchText = v.findViewById(R.id.trending_search_text)
        goButton = v.findViewById(R.id.go)
        recycler_view2 = v.findViewById(R.id.recycler_view2)
        trending_post_text=v.findViewById(R.id.trending_post_text)
        trandingBackButton=v.findViewById(R.id.back_arrow_tranding)
        try {
            latitude = SavedPrefManager.getLatitudeLocation()!!
            longitude = SavedPrefManager.getLongitudeLocation()!!
            catId = arguments?.getString("CAT_ID")!!
            maxDis = arguments?.getInt("MAX_DIS")!!
        } catch(e : java.lang.Exception) {
            e.printStackTrace()
        }
        getTrendingPostApi()
        trandingBackButton.setOnClickListener{
            fragmentManager?.beginTransaction()?.replace(R.id.linear_layout, TrendingFragment())?.commit()

        }
        goButton.setOnClickListener{
            searchValue = searchText.text.toString()
            getTrendingPostApi()
        }

        textLocalPostTrending=v.findViewById(R.id.text_local_post_trending)
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
            textFollowingPostTrending.setTextColor(resources.getColor(R.color.orange))
            trending_post_text.setText("Following Activity")
            textLocalPostTrending.setTextColor(resources.getColor(R.color.white))
            trandingBackButton.visibility = View.VISIBLE
            userTrendingImg.visibility = View.GONE
            filter.visibility =View.GONE
        }

        userTrendingImg=v.findViewById(R.id.user_treanding_img)
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
                secondFragment("trending")
            )
                ?.commit()

        }
        return v


    }

    private fun getTrendingPostApi() {
        if (androidextention.isOnline(mContext)) {
            androidextention.showProgressDialog(mContext)
            val serviceManager = ServiceManager(mContext)
            val callBack: ApiCallBack<LocalActivityResponse> =
                ApiCallBack<LocalActivityResponse>(this, "LocalActivity", mContext)

            val apiRequest = Api_Request()
            apiRequest.categoryId = catId
            apiRequest.maxDistance = maxDis.toString()
            apiRequest.search = searchValue

            try {
                if (catId != null && !catId.equals("") || maxDis != null && maxDis > 0) {
                    serviceManager.getTrendingPost(callBack, latitude, longitude, apiRequest)

                } else if (searchValue != null && !searchValue.equals("")) {
                    serviceManager.getTrendingPost(callBack, null, null, apiRequest)
                } else {
                    serviceManager.getTrendingPost(callBack, null, null, apiRequest)
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
        androidextention.disMissProgressDialog(activity)
        Toast.makeText(activity, "Data not found", Toast.LENGTH_LONG).show()
    }

    override fun onApiFailure(failureMessage: String?, apiName: String?) {
        androidextention.disMissProgressDialog(activity)
        Toast.makeText(activity, "Something want wrong", Toast.LENGTH_LONG).show()
    }

    fun setAdapter(list: ArrayList<Docss>) {
        adaptor = this?.let { TrendingListAdaptor(it, list,this) }!!
        val layoutManager = GridLayoutManager(activity,2)
        recycler_view2?.layoutManager = layoutManager
        recycler_view2?.adapter = adaptor
        adaptor.notifyDataSetChanged()
    }

    override fun customClick(value: Docss, type: String)   {
        USERID =   value._id

        if (type.equals("profile")){

            var intent = Intent(mContext, PostActivity::class.java)
//            intent.putExtra("userId", USERID)
            SavedPrefManager.saveStringPreferences(mContext, SavedPrefManager._id, USERID)

            startActivity(intent)
        }
    }
}



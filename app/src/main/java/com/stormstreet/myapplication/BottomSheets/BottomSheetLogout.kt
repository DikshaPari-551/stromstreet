

package com.stormstreet.myapplication.BottomSheets

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import com.stormstreet.myapplication.LoginActivity
import com.stormstreet.myapplication.R
import com.stormstreet.myapplication.customclickListner.LogoutClickListener
import com.stormstreet.myapplication.entity.ApiCallBack
import com.stormstreet.myapplication.entity.Response.Responce
import com.stormstreet.myapplication.entity.Service_Base.ApiResponseListener
import com.stormstreet.myapplication.entity.Service_Base.ServiceManager
import com.stormstreet.myapplication.extension.androidextention
import com.stormstreet.myapplication.util.SavedPrefManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class BottomSheetLogout(var logoutClickListener: LogoutClickListener) : BottomSheetDialogFragment(), ApiResponseListener<Responce> {
    var pic_id = 123
    private val pickImage = 100
    lateinit var cancel_logout: TextView
    lateinit var logout: TextView
    lateinit var mContext: Context



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var v = inflater.inflate(R.layout.logout_screen, container, false)
        mContext = activity!!


        logout=v.findViewById(R.id.logout)


        cancel_logout = v.findViewById(R.id.cancel_logout)
//          var bottomsheetLogout=
//              BottomSheetLogout(logoutClickListener)

        logout.setOnClickListener{
            logoutApi()
            logoutClickListener.logoutClick(true)
//            var intent =Intent(activity,
//                LoginActivity::class.java)
//            startActivity(intent)
//            MainActivity().finish()
//            (context as Activity).finishAffinity()
        }
        cancel_logout.setOnClickListener {
           dismiss()
        }
        return v
    }

    override fun getTheme(): Int =
        R.style.BottomSheetDialogTheme
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog = BottomSheetDialog(requireContext(), theme)

    private fun logoutApi() {
//        val Token = SavedPrefManager.getStringPreferences(this, SavedPrefManager.TOKEN).toString()


        if (androidextention.isOnline(mContext)) {
//            androidextention.showProgressDialog(mContext)
//            lottie.initLoader(true)
            val serviceManager = ServiceManager(mContext)
            val callBack: ApiCallBack<Responce> =
                ApiCallBack<Responce>(this, "Logout", mContext)
//            val apiRequest = Api_Request()

//            apiRequest.email = emailSignUp_et.getText().toString().trim()


            try {
                serviceManager.getLogout(callBack)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    override fun onApiSuccess(response: Responce, apiName: String?) {
//        Toast.makeText(activity, "success", Toast.LENGTH_LONG).show()
//        lottie.initLoader(false)
        SavedPrefManager.saveStringPreferences(activity,SavedPrefManager.KEY_IS_LOGIN, "false")

        var intent =Intent(activity,
                LoginActivity::class.java)
            startActivity(intent)
//            MainActivity().finish()
            (context as Activity).finishAffinity()
    }

    override fun onApiErrorBody(response: String?, apiName: String?) {
//        lottie.initLoader(false)
        Toast.makeText(activity, "error", Toast.LENGTH_LONG).show()

    }

    override fun onApiFailure(failureMessage: String?, apiName: String?) {
//        lottie.initLoader(false)
        Toast.makeText(activity, "fail", Toast.LENGTH_LONG).show()

    }


}
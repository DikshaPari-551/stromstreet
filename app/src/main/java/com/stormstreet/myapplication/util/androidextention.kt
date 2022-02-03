package  com.stormstreet.myapplication.util

import android.app.Activity
import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.LinearLayout

import android.net.ConnectivityManager
import android.widget.Toast
import java.util.*


object androidextention {


    var dialogProgress: ProgressDialog? = null

    fun <T> Context.activityIntent(startingReference: Activity, destinationReference: Class<T>) {
        val intent = Intent(startingReference, destinationReference)
        startActivity(intent)
    }

    fun <T> Context.activityIntentExtra(it: Class<T>, bundleKey: String, bundle: Bundle) {
        var intent = Intent(this, it)
        intent.putExtra(bundleKey, bundle)
        startActivity(intent)
    }

    fun showProgressDialog(context: Context?) {
        if (context != null) {
            try {
                dialogProgress?.dismiss()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        dialogProgress = ProgressDialog(context)
        if (dialogProgress!!.getWindow() != null) {
            dialogProgress!!.getWindow()!!.setLayout(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
        }
        dialogProgress!!.setMessage("Please wait.")
        if (!dialogProgress!!.isShowing()) {
            try {
                dialogProgress?.show()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        dialogProgress!!.setCancelable(false)
    }

    fun disMissProgressDialog(mContext: Context?) {
        try {
            if (dialogProgress != null) {
                dialogProgress?.dismiss()
                dialogProgress = null
            }
        }catch (e : Exception) {
            e.printStackTrace()
        }
    }

    fun alertBox(message: String,context: Context) {
        var alertDialog: AlertDialog? = null
        val builder = AlertDialog.Builder(context)
        builder.setMessage(message)
        builder.setPositiveButton("ok") { dialogInterface, which ->
            alertDialog!!.dismiss()
        }
        alertDialog = builder.create()
        alertDialog!!.setCancelable(false)
        alertDialog!!.show()
    }




    fun isOnline(context: Context): Boolean {
        val conMgr = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val netInfo = conMgr.activeNetworkInfo
        return !(netInfo == null || !netInfo.isConnected || !netInfo.isAvailable)
    }
    
    






}
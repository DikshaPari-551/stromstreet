package com.example.myapplication.BottomSheets

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import com.example.myapplication.Activities.PostActivity
import com.example.myapplication.R
import com.example.myapplication.customclickListner.ClickListnerDelete
import com.example.myapplication.entity.Service_Base.ServiceManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class BottomSheetOptions(var click : ClickListnerDelete) : BottomSheetDialogFragment(){
    lateinit var mContext : Context
    lateinit var serviceManager: ServiceManager
    lateinit var deletepost: TextView
    lateinit var cancel: TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var v = inflater.inflate(R.layout.bottom_options, container, false)
        mContext = activity!!.applicationContext
        serviceManager = ServiceManager(mContext)
        deletepost = v.findViewById(R.id.deletepost)
        cancel = v.findViewById(R.id.cancel)


        cancel.setOnClickListener {
            dismiss()
        }
        deletepost.setOnClickListener {
          click.deletePost()
        }
        return v
    }




}
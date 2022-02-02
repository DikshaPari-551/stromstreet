package com.stormstreet.myapplication.BottomSheets

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.stormstreet.myapplication.R
import com.stormstreet.myapplication.customclickListner.ClickListnerDelete
import com.stormstreet.myapplication.entity.Service_Base.ServiceManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class BottomSheetOptions(var click: ClickListnerDelete,var tagdata: String) : BottomSheetDialogFragment(){
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
//=========================
        if(tagdata.equals("delete"))
        {
            deletepost.setText("Delete Post")
        }
        else
        if(tagdata.equals("block"))
        {
            deletepost.setText("Block User")
        }
        else
        {
            deletepost.setText("Report")
        }

        cancel.setOnClickListener {
            dismiss()
        }
        deletepost.setOnClickListener {
            if(tagdata!!.equals("delete"))
            {
                click.deletePost()
            }
            else
                if(tagdata.equals("block"))
                {
                    click.deletePost()
                }
            else
            {
                click.reportpost()
            }

        }
        return v
    }




}
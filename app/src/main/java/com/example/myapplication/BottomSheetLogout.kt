package com.example.myapplication

import android.app.Dialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.io.File

class BottomSheetLogout : BottomSheetDialogFragment() {
    var pic_id = 123
    private val pickImage = 100
    lateinit var cancel_logout: TextView
    lateinit var logout: TextView
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var v = inflater.inflate(R.layout.logout_screen, container, false)


        logout=v.findViewById(R.id.logout)
        cancel_logout = v.findViewById(R.id.cancel_logout)
          var bottomsheetLogout=BottomSheetLogout()

        logout.setOnClickListener{
            var intent =Intent(activity,LoginActivity::class.java)
            startActivity(intent)
        }
        cancel_logout.setOnClickListener {
           dismiss()
        }
        return v
    }
    override fun getTheme(): Int = R.style.BottomSheetDialogTheme
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog = BottomSheetDialog(requireContext(), theme)
}
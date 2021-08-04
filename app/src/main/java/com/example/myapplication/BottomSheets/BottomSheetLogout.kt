package com.example.myapplication.BottomSheets

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.myapplication.Fragments.ProfileChangeFragment
import com.example.myapplication.LoginActivity
import com.example.myapplication.MainActivity
import com.example.myapplication.R
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

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
          var bottomsheetLogout=
              BottomSheetLogout()

        logout.setOnClickListener{
            var intent =Intent(activity,
                LoginActivity::class.java)
            startActivity(intent)
            MainActivity().finish()
        }
        cancel_logout.setOnClickListener {
           dismiss()
        }
        return v
    }
    override fun getTheme(): Int =
        R.style.BottomSheetDialogTheme
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog = BottomSheetDialog(requireContext(), theme)
}
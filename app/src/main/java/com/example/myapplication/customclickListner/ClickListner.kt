package com.example.myapplication.customclickListner

import android.content.Intent
import com.example.myapplication.bottomSheetDialog

interface ClickListner {
    fun clickListner(
        requestCode: Int,
        resultCode: Int,
        data: Intent?,
        bottomSheetDialog: bottomSheetDialog,
        imagePath: String
    )
}
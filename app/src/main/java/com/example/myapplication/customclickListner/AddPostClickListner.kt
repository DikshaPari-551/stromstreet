package com.example.myapplication.customclickListner

import android.content.Intent
import com.example.myapplication.bottomSheetDialog

interface AddPostClickListner {
    fun addPostClickListner(
        requestCode: Int,
        resultCode: Int,
        data: Intent?,
        bottomSheetDialog: bottomSheetDialog
    )
}
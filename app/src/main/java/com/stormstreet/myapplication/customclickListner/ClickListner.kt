package com.stormstreet.myapplication.customclickListner

import android.content.Intent
import com.stormstreet.myapplication.bottomSheetDialog

interface ClickListner {
    fun clickListner(
        requestCode: Int,
        resultCode: Int,
        data: Intent?,
        bottomSheetDialog: bottomSheetDialog,
        imagePath: String
    )
}
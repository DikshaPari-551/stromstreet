package com.example.myapplication.util

import android.net.Uri
import java.io.File

class FileUpload {
    companion object {
        private var imageFile: Uri? = null

        fun setImageFile(imageFile: Uri) {
            this.imageFile = imageFile
        }

        fun getImageFile(): Uri? {
            return imageFile
        }
    }
}
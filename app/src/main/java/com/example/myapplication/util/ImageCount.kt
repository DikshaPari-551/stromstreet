package com.example.myapplication.util

import android.net.Uri
import java.io.File

class ImageCount {
    companion object {
       private var imageCount: Int = 0

        fun setImageCount(imageCount: Int) {
            this.imageCount = imageCount
        }

        fun getImageCount(): Int? {
            return imageCount
        }
    }
}
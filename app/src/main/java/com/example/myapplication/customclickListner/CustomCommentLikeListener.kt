package com.example.myapplication.customclickListner

import android.widget.ImageView

interface CustomCommentLikeListener {
    fun commentLikeListener(_id: String, commentLike: ImageView)
}
package com.stormstreet.myapplication.customclickListner

import androidx.recyclerview.widget.RecyclerView

interface CustomReplyListener {
    fun replyListener(commentRepliesRecyclerView: RecyclerView, position: Int, _id: String)
}
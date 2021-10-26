package com.example.myapplication.customclickListner

import com.example.myapplication.entity.Response.Docs
import com.example.myapplication.entity.Response.Docss

interface CustomClickListner {
    fun customClick(value: Docs, type:String)

}

interface CustomClickListner2 {
    fun customClick(value: Docss, type:String)

}
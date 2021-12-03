package com.example.myapplication.customclickListner

import com.example.myapplication.entity.Response.Docs
import com.example.myapplication.entity.Response.Docss
import com.example.myapplication.entity.Response.UserPostDocs

interface CustomClickListner {
    fun customClick(value: Docs, type:String)

}

interface CustomClickListner2 {
    fun customClick(value: Docss, type:String)

}

interface CustomClickListner3 {
    fun customClick(value: UserPostDocs, type:String)

}
interface CustomClickListnerdelete {
    fun customClick(value: Docss, type:String,i:Int)

}
interface CustomClickListneruserpost {
    fun customClick(value: UserPostDocs, type:String,i:Int)

}
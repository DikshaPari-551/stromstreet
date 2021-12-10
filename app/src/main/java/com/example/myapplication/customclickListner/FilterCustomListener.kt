package com.example.myapplication.customclickListner

import com.example.myapplication.entity.Response.CategoryResult

interface FilterCustomListener {

    fun filterCustomListener(categoryName: ArrayList<CategoryResult>)
}
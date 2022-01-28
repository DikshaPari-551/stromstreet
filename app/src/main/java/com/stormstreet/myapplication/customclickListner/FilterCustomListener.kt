package com.stormstreet.myapplication.customclickListner

import com.stormstreet.myapplication.entity.Response.CategoryResult

interface FilterCustomListener {

    fun filterCustomListener(categoryName: ArrayList<CategoryResult>)
}
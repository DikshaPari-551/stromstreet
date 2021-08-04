package com.example.myapplication

object LoginFlag {

    private var loginFlag: Boolean = false

    fun setLoginFlag(flag : Boolean) {
        loginFlag = flag
    }

    fun getLoginFlag(): Boolean {
        return loginFlag
    }

}
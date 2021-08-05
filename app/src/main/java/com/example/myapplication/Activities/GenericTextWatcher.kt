package com.example.myapplication.Activities

import android.R.attr.editable
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import com.example.myapplication.R

//
//class GenericTextWatcher: TextWatcher {
//     var editText: Array<EditText> = arrayOf()
//    lateinit var view: View
//
//    constructor(editText: Array<EditText>, view: View) {
//        this.editText = editText
//        this.view = view
//    }
//
////   fun GenericTextWatcher(editText: Array<EditText>, view: View) {
////        this.editText = editText
////        this.view = view
////    }
//
//
//    override fun afterTextChanged(s: Editable?) {
//        val text = editable.toString()
//        when (view.getId()) {
//
//             R.id.et_1  ->
//            if (text.length==1) {
//                editText[1].requestFocus();
//            }
//           R.id.et_2->
//               if (text.length==1) {
//                editText[2].requestFocus();
//            }
//            else if (text.length == 0) {
//                   editText[0].requestFocus();
//               }
//            R.id.et_2->
//                if (text.length==1) {
//                    editText[3].requestFocus();
//                }
//                else if (text.length == 0) {
//                    editText[1].requestFocus();
//                }
//            R.id.et_2->
//                if (text.length==0) {
//                    editText[2].requestFocus();
//                }
//
//    }
//
//
//
//}
//
//    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
//        TODO("Not yet implemented")
//    }
//
//    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
//        TODO("Not yet implemented")
//    }
//
//}

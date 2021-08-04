package com.example.myapplication.Activities

import android.content.ClipData
import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.R
import java.io.File

class ImageActivity : AppCompatActivity() {
    lateinit var image:ImageView
    var file : File? = null
    var PICK_IMAGE_MULTIPLE = 1


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image)
       image=findViewById(R.id.imagevieww)


    }

//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        if (requestCode == PICK_IMAGE_MULTIPLE && resultCode == RESULT_OK && null != data) {
//            // Get the Image from data
//            if (data.getClipData() != null) {
//                var mClipData = data.getClipData();
//                var cout = data.getClipData().getItemCount();
//                for (int i = 0; i < cout; i++) {
//                    // adding imageuri in array
//                    Uri imageurl = data.getClipData().getItemAt(i).getUri();
//                    mArrayUri.add(imageurl);
//                }
//                // setting 1st selected image into image switcher
//                image.setImageURI(mArrayUri.get(0));
//                position = 0;
//            } else {
//                Uri imageurl = data.getData();
//                mArrayUri.add(imageurl);
//                imageView.setImageURI(mArrayUri.get(0));
//                position = 0;
//            }
//        } else {
//            // show this if no image is selected
//            Toast.makeText(this, "You haven't picked Image", Toast.LENGTH_LONG).show();
//        }
//    }
    }

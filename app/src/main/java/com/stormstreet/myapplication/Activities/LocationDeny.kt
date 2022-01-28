package com.stormstreet.myapplication.Activities

import android.content.Intent
import android.os.Bundle
import android.widget.RelativeLayout
import androidx.appcompat.app.AppCompatActivity
import com.stormstreet.myapplication.R

class LocationDeny : AppCompatActivity() {
    lateinit var allowaccess : RelativeLayout


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_location_deny)
        allowaccess = findViewById(R.id.allowaccess)

        allowaccess.setOnClickListener {
            val intent = Intent(this, SplashActivity::class.java)
            intent.addFlags(
                Intent.FLAG_ACTIVITY_CLEAR_TOP or
                        Intent.FLAG_ACTIVITY_CLEAR_TASK or
                        Intent.FLAG_ACTIVITY_NEW_TASK
            )
            startActivity(intent)

        }
    }
}
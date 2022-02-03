package com.stormstreet.myapplication.Activities

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.view.ViewGroup
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.stormstreet.myapplication.LoginActivity
import com.stormstreet.myapplication.MainActivity
import com.stormstreet.myapplication.R
import com.stormstreet.myapplication.util.FacebookHandler
import com.stormstreet.myapplication.util.SavedPrefManager


class SplashActivity : AppCompatActivity() {
    private val LOCATION_PERMISSION_REQ_CODE = 1000;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_splash)
        locationpermission()

        if (Build.VERSION.SDK_INT >= 21) {
            val window = window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.statusBarColor = resources.getColor(R.color.black)
        }
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        // we used the postDelayed(Runnable, time) method
        // to send a message with a delayed time.
//        Handler().postDelayed({
//            val intent = Intent(this, MainActivity::class.java)
//            startActivity(intent)
//            finish()
//        }, 3000) // 3000    // is the delayed time in milliseconds.
//        if (ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED) {
//
//            // If you do not have permission, request it
//            ActivityCompat.requestPermissions(this as Activity,arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),LOCATION_PERMISSION_REQ_CODE)
//        } else {
//            // Launch the camera if the permission exists
//            Handler().postDelayed({
//                val intent = Intent(this, MainActivity::class.java)
//                startActivity(intent)
//                finish()
//            }, 3000)
//        }
        FacebookHandler.printHashKey(this)
    }

    private fun locationpermission() {

        Handler().postDelayed({
            if ((SavedPrefManager.getStringPreferences(this, SavedPrefManager.KEY_IS_LOGIN)
                    .equals("true"))
            ) {
//                throw RuntimeException("Test Crash") // Force a crash
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }
        }, 3000)

    }




    private fun ifdenaye() {
//        val intent = Intent(this, LocationDeny::class.java)
//
    }

}


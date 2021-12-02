package com.example.myapplication.Activities

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.example.myapplication.MainActivity
import com.example.myapplication.R
import com.example.myapplication.util.SavedPrefManager
import com.google.android.gms.location.LocationServices


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

    }

    private fun locationpermission() {

        // checking location permission
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {

            // If you do not have permission, request it
            ActivityCompat.requestPermissions(
                this as Activity,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                LOCATION_PERMISSION_REQ_CODE
            )
        } else {
            // Launch the camera if the permission exists
            Handler().postDelayed({
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }, 3000)
        }
        var fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location ->
                // getting the last known or current location
                try {
                    var latitude = location.latitude
                    var longitude = location.longitude
//                    val lm = getSystemService(Context.LOCATION_SERVICE) as LocationManager
//                    val locatiodata: Location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER)
//                    val longitudenew : Double = locatiodata.getLongitude()
//                    val latitudenew: Double = locatiodata.getLatitude()
                    SavedPrefManager.setLatitudeLocation(latitude)
                    SavedPrefManager.setLongitudeLocation(longitude)

                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
            .addOnFailureListener {
            }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String?>,
        grantResults: IntArray
    ) {
        // Called when you request permission to read and write to external storage
        when (requestCode) {
            LOCATION_PERMISSION_REQ_CODE -> {
                if (grantResults.size > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED
                ) {
                    // If you get permission, launch the camera
                    Handler().postDelayed({
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    }, 3000)
                } else {
                    // If you do not get permission, show a Toast
//                    Toast.makeText(
//                        this, "Permission Denied",
//                        Toast.LENGTH_SHORT
//                    ).show()
                    ifdenaye()
                }
            }
        }
    }


    private fun ifdenaye() {
//        val intent = Intent(this, LocationDeny::class.java)
//        startActivity(intent)
        AlertDialog.Builder(this)
            .setTitle("Permissions Needed")
            .setMessage("This application needs some permissions, Please accept all the permissions to use this application.")
            .setPositiveButton(
                "OK"
            ) { _, _ ->
                //Prompt the user once explanation has been shown
//                locationpermission()
                System.exit(0)
            }
            .create()
            .show()
    }

}


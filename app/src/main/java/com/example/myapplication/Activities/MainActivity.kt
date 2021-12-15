package com.example.myapplication

import android.Manifest
import android.app.Activity
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.view.WindowManager
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.myapplication.Activities.NoInternetActivity
import com.example.myapplication.Fragments.*
import com.example.myapplication.entity.Response.Chalist
import com.example.myapplication.entity.Response.Messages
import com.example.myapplication.entity.permission.MarshMallowPermission
import com.example.myapplication.entity.permission.RequestPermission
import com.example.myapplication.extension.androidextention
import com.example.myapplication.socket.SocketManager
import com.example.myapplication.util.SavedPrefManager
import com.google.android.gms.location.LocationServices
import java.io.*


class MainActivity : AppCompatActivity()
{
    lateinit var menu: ImageView
    lateinit var bubble: ImageView
    lateinit var profile: ImageView
    lateinit var add: ImageView
    private var loginFlag: Boolean = false
    lateinit var user_home: ImageView
    lateinit var filter: ImageView
    lateinit var topText: TextView
    var file: File? = null
    private var GALLERY = 1
    private var CAMERA: Int = 2
    val CAMERA_PERM_CODE = 101
    private val LOCATION_PERMISSION_REQ_CODE = 1000;

    var marshMallowPermission: MarshMallowPermission? = null

    lateinit var socketInstance: SocketManager


    lateinit var chat: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        locationpermission()
        RequestPermission.requestMultiplePermissions(this)
//        locationpermission()
//        marshMallowPermission =MarshMallowPermission(this)
        if (Build.VERSION.SDK_INT >= 21) {
            val window = window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.statusBarColor = resources.getColor(R.color.black)
        }



        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.CAMERA
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.CAMERA),
                CAMERA_PERM_CODE
            )
        }
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            val window: Window = window
//            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
//            window.setStatusBarColor(Color.)
//        }
        menu = findViewById(R.id.menu)
        bubble = findViewById(R.id.bubble)
        profile = findViewById(R.id.profile)
        add = findViewById(R.id.add)

        loginFlag = LoginFlag.getLoginFlag()


        menu.setOnClickListener {

            supportFragmentManager.beginTransaction().replace(R.id.linear_layout, HomeFragment())
                .commit()
            profile.setColorFilter(resources.getColor(R.color.grey))
            menu.setColorFilter(resources.getColor(R.color.white))
            bubble.setColorFilter(resources.getColor(R.color.grey))

            chat.setColorFilter(resources.getColor(R.color.grey))
        }
        chat = findViewById(R.id.chat)
        chat.setColorFilter(resources.getColor(R.color.grey))

        chat.setOnClickListener {
            supportFragmentManager.beginTransaction().replace(
                R.id.linear_layout,
                TrendingFragment()
            ).addToBackStack(null).commit()

            profile.setColorFilter(resources.getColor(R.color.grey))
            menu.setColorFilter(resources.getColor(R.color.grey))
            bubble.setColorFilter(resources.getColor(R.color.grey))
            chat.setColorFilter(resources.getColor(R.color.white))

        }
        add.setOnClickListener {
            RequestPermission.requestMultiplePermissions(this)

            if (SavedPrefManager.getStringPreferences(this, SavedPrefManager.KEY_IS_LOGIN)
                    .equals("true")
            ) {
//                supportFragmentManager.beginTransaction().replace(
//                    R.id.linear_layout,
//                    AddPostFragment(null,null,null,null,null)
//                ).commit()
                supportFragmentManager.beginTransaction().replace(
                    R.id.linear_layout,
                    AddPostFragment()
                ).commit()

                profile.setColorFilter(resources.getColor(R.color.grey))
                menu.setColorFilter(resources.getColor(R.color.grey))
                bubble.setColorFilter(resources.getColor(R.color.grey))
                chat.setColorFilter(resources.getColor(R.color.grey))
            } else {
                val i = Intent(this, LoginActivity::class.java)
                startActivity(i)
            }
        }
        bubble.setColorFilter(resources.getColor(R.color.grey))

        bubble.setOnClickListener {
            if (SavedPrefManager.getStringPreferences(this, SavedPrefManager.KEY_IS_LOGIN)
                    .equals("true")
            ) {


                profile.setColorFilter(resources.getColor(R.color.grey))
                menu.setColorFilter(resources.getColor(R.color.grey))
                bubble.setColorFilter(resources.getColor(R.color.white))
                chat.setColorFilter(resources.getColor(R.color.grey))
                supportFragmentManager.beginTransaction()
                    .replace(R.id.linear_layout, ChatFragment()).commit()
            } else {
                val i = Intent(this, LoginActivity::class.java)
                startActivity(i)

            }
        }
        profile.setColorFilter(resources.getColor(R.color.grey))

        profile.setOnClickListener {
            if (SavedPrefManager.getStringPreferences(this, SavedPrefManager.KEY_IS_LOGIN)
                    .equals("true")
            ) {
                profile.setColorFilter(resources.getColor(R.color.white))
                menu.setColorFilter(resources.getColor(R.color.grey))
                bubble.setColorFilter(resources.getColor(R.color.grey))
                chat.setColorFilter(resources.getColor(R.color.grey))
                if (androidextention.isOnline(this)) {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.linear_layout, ProfileFragment()).commit()
                } else {
                    val intent = Intent(this, NoInternetActivity::class.java)
                    startActivity(intent)
                }
            } else {
                val i = Intent(this, LoginActivity::class.java)
                startActivity(i)
            }

        }
        add.setBackgroundColor(resources.getColor(R.color.orange))
        supportFragmentManager.beginTransaction().add(R.id.linear_layout, HomeFragment()).commit()

        socketInstance = SocketManager.getInstance(this)

        initializeSocket()



    }

    private fun initializeSocket() {
        onAddListeners()
        if (!socketInstance.isConnected) {
            socketInstance.connect()
        } else {
            //   onlineStatus()

        }

    }

    private fun onAddListeners() {

        socketInstance.initialize(object : SocketManager.SocketListener {
            override fun onConnected() {
                Log.e("browse_page_err", "omd " + "onConnected")

                // onlineStatus()
            }

            override fun onDisConnected() {
                socketInstance.connect()
            }

            override fun chatlist(listdat: ArrayList<Chalist>) {

            }

            override fun viewchat(listdat: ArrayList<Messages>) {

            }

            override fun oneToOneChat(listdat: Messages) {

            }
        })
    }



//    override fun onActivityResult(requestCode: Int, resultCode: Int, d: Intent?) {
//        super.onActivityResult(requestCode, resultCode, d)
//        if(resultCode == AppCompatActivity.RESULT_OK){
//            if(requestCode == 1) {
//                file = File(Environment.getExternalStorageDirectory().toString())
//                for (temp in file!!.listFiles()) {
//                    if (temp.name == "temp.jpg") {
//                        file = temp
//                        break
//                    }
//                }
//            }
//        }
//        val bitmap: Bitmap
//        if (requestCode == GALLERY) {
//            try {
//                val selectedImage: Uri? = d?.data
//
//                val filePath = arrayOf(MediaStore.Images.Media.DATA)
//                val c: Cursor? =
//                    contentResolver.query(selectedImage!!, filePath, null, null, null)
//                c?.moveToFirst()
//                val columnIndex: Int = c!!.getColumnIndex(filePath[0])
//                val picturePath: String = c.getString(columnIndex)
//                c?.close()
//                val thumbnail = BitmapFactory.decodeFile(picturePath)
////                    Log.w(
////                        "path of image from gallery",
////                        picturePath + ""
////                    )
//                supportFragmentManager?.beginTransaction()?.replace(R.id.layout,AddPostFragment())?.commit()
//                finish()
//            } catch (e: Exception) {
//                e.printStackTrace()
//            }
//        } else if (requestCode == CAMERA) {
//            try {
//                val bitmapOptions = BitmapFactory.Options()
//                bitmap = BitmapFactory.decodeFile(
//                    file?.absolutePath,
//                    bitmapOptions
//                )
////                frontImage.setImageBitmap(bitmap)
//                supportFragmentManager?.beginTransaction()?.replace(R.id.layout,AddPostFragment())?.commit()
//                finish()
//                val path = (Environment
//                    .getExternalStorageDirectory()
//                    .toString() + File.separator
//                        + "Phoenix" + File.separator + "default")
//                file?.delete()
//                var outFile: OutputStream? = null
//                val file = File(path, System.currentTimeMillis().toString() + ".jpg")
//                try {
//                    outFile = FileOutputStream(file)
//                    bitmap.compress(Bitmap.CompressFormat.JPEG, 85, outFile)
//                    outFile.flush()
//                    outFile.close()
//                } catch (e: FileNotFoundException) {
//                    e.printStackTrace()
//                } catch (e: IOException) {
//                    e.printStackTrace()
//                } catch (e: Exception) {
//                    e.printStackTrace()
//                }
//            } catch (e: Exception) {
//                e.printStackTrace()
//            }
//        }
//
//    }

    private fun locationpermission() {
        // checking location permission
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // request permission
            ActivityCompat.requestPermissions(
                this as Activity,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                LOCATION_PERMISSION_REQ_CODE
            );
            return
        }
        var fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location ->
                // getting the last known or current location
                try {
                    var latitude = location.latitude
                    var longitude = location.longitude
                    SavedPrefManager.setLatitudeLocation(latitude)
                    SavedPrefManager.setLongitudeLocation(longitude)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
            .addOnFailureListener {
                Toast.makeText(
                    this, "Failed on getting current location",
                    Toast.LENGTH_SHORT
                ).show()
            }
    }


}
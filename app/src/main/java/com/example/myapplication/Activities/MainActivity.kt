package com.example.myapplication

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.WindowManager
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.example.myapplication.Fragments.ChatFragment
import com.example.myapplication.Fragments.HomeFragment
import com.example.myapplication.Fragments.ProfileFragment
import com.example.myapplication.Fragments.TrendingFragment
import com.example.myapplication.entity.permission.RequestPermission
import com.example.myapplication.util.AppConst
import com.example.myapplication.util.SavedPrefManager
import java.io.*


class MainActivity : AppCompatActivity() {
   lateinit var menu:ImageView
    lateinit var bubble:ImageView
    lateinit var profile:ImageView
    lateinit var add:ImageView
    private var loginFlag : Boolean = false
   lateinit var user_home:ImageView
    lateinit var filter:ImageView
    lateinit var  topText:TextView
    var file : File? = null
    private var GALLERY = 1
    private  var CAMERA:Int = 2


    lateinit var chat:ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (Build.VERSION.SDK_INT >= 21) {
            val window = window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.statusBarColor = resources.getColor(R.color.black)
        }
        if (  SavedPrefManager.getStringPreferences(this,  SavedPrefManager.KEY_IS_LOGIN).equals("true")) {
            RequestPermission.requestMultiplePermissions(this)
        }
        menu=findViewById(R.id.menu)
        bubble=findViewById(R.id.bubble)
        profile=findViewById(R.id.profile)
        add=findViewById(R.id.add)




        loginFlag = LoginFlag.getLoginFlag()

        menu.setOnClickListener{
            supportFragmentManager.beginTransaction().replace(R.id.linear_layout, HomeFragment()).commit()
            profile.setColorFilter(resources.getColor(R.color.grey))
            menu.setColorFilter(resources.getColor(R.color.white))
            bubble.setColorFilter(resources.getColor(R.color.grey))

            chat.setColorFilter(resources.getColor(R.color.grey))
        }
        chat=findViewById(R.id.chat)
        chat.setColorFilter(resources.getColor(R.color.grey))

        chat.setOnClickListener{
            supportFragmentManager.beginTransaction().replace(
                R.id.linear_layout,
                TrendingFragment()
            ).commit()

            profile.setColorFilter(resources.getColor(R.color.grey))
            menu.setColorFilter(resources.getColor(R.color.grey))
            bubble.setColorFilter(resources.getColor(R.color.grey))
            chat.setColorFilter(resources.getColor(R.color.white))




        }
        add.setOnClickListener{
            if (  SavedPrefManager.getStringPreferences(this,  SavedPrefManager.KEY_IS_LOGIN).equals("true")) {
                var bottomsheet = bottomSheetDialog("addpost",null)
                bottomsheet.show(supportFragmentManager, "bottomsheet")
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

        bubble.setOnClickListener{
            if (  SavedPrefManager.getStringPreferences(this,  SavedPrefManager.KEY_IS_LOGIN).equals("true")) {
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

        profile.setOnClickListener{
            if(  SavedPrefManager.getStringPreferences(this,  SavedPrefManager.KEY_IS_LOGIN).equals("true")){
                profile.setColorFilter(resources.getColor(R.color.white))
                menu.setColorFilter(resources.getColor(R.color.grey))
                bubble.setColorFilter(resources.getColor(R.color.grey))
                chat.setColorFilter(resources.getColor(R.color.grey))
                supportFragmentManager.beginTransaction()
                    .replace(R.id.linear_layout, ProfileFragment()).commit()
            } else {
                val i = Intent(this, LoginActivity::class.java)
                startActivity(i)
            }

        }
        add.setBackgroundColor(resources.getColor(R.color.orange))
        supportFragmentManager.beginTransaction().add(R.id.linear_layout, HomeFragment()).commit()




    }

    override fun onDestroy() {
        super.onDestroy()
        SavedPrefManager.saveStringPreferences(this, AppConst.IMAGEDATA, "false")
    }

}
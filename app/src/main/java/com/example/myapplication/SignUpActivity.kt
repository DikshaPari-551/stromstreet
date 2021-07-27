package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView

class SignUpActivity : AppCompatActivity() {
    lateinit var check: CheckBox
    lateinit var login: TextView
    lateinit var layout_signup: RelativeLayout
    lateinit var background: RelativeLayout
    lateinit var camera: ImageView
    lateinit var error_text: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        camera = findViewById(R.id.img_camera)
        login = findViewById(R.id.text_login)
        check = findViewById(R.id.check)
        layout_signup = findViewById(R.id.layout_signup)
        background = findViewById(R.id.background_error)
        error_text = findViewById(R.id.textView_error)

        camera.setOnClickListener{
            var bottomsheet=bottomSheetDialog()
            bottomsheet.show(supportFragmentManager,"bottomsheet")
        }
        login.setOnClickListener {
            var intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        layout_signup.setOnClickListener {
            if (!check.isChecked) {
                background.setBackgroundResource(R.drawable.background_error)
                error_text.setText("Accepting checkbox is necessary")
            } else {
                error_text.setText("")
                background.setBackgroundResource(R.drawable.drawable_back)
                var intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
        }
    }
}
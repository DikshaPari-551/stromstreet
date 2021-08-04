package com.example.myapplication.Activities

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.R
import com.example.myapplication.ValidationExt.Validations
import com.example.myapplication.BottomSheets.bottomSheetDialog

class SignUpActivity : AppCompatActivity() {
    lateinit var check: CheckBox
    lateinit var nameSignUp: TextView
    lateinit var emailSignUp_text: TextView


    private val PASSWORD_PATTERN =

        "^" +
                "(?=.*[@#$%^&+=])" +  // at least 1 special character
                "(?=\\S+$)" +  // no white spaces
                ".{4,}" +  // at least 4 characters
                "$"

    lateinit var password_et: EditText
    lateinit var password_text: TextView


    lateinit var phone_et: EditText
    lateinit var phone_text: TextView

    lateinit var emailSignUp_et: EditText
    lateinit var username_text: TextView
    lateinit var username_et: EditText
    lateinit var login: TextView
    lateinit var layout_signup: RelativeLayout
    lateinit var background: RelativeLayout
    lateinit var camera: ImageView
    lateinit var sign_up_full_name: EditText
    lateinit var error_text: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        emailSignUp_et = findViewById(R.id.email_sign_etext)

        phone_et = findViewById(R.id.phonenumber_et)
        phone_text = findViewById(R.id.phone_sign_text)

        username_et = findViewById(R.id.username_et)
        username_text = findViewById(R.id.username_sign_text)

        password_et = findViewById(R.id.password_sign_et)
        password_text = findViewById(R.id.password_sign_text)

        emailSignUp_text = findViewById(R.id.email_sign_text)
        sign_up_full_name = findViewById(R.id.fullname_text)
        nameSignUp = findViewById(R.id.name_sign_up)
        camera = findViewById(R.id.img_camera)
        login = findViewById(R.id.text_login)
        check = findViewById(R.id.check)
        layout_signup = findViewById(R.id.layout_signup)
        background = findViewById(R.id.background_error)
        error_text = findViewById(R.id.textView_error)

        camera.setOnClickListener {
            var bottomsheet =
                bottomSheetDialog()
            bottomsheet.show(supportFragmentManager, "bottomsheet")
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


            CheckValidations()
        }
    }
    fun CheckValidations(){
        var fullnameSignUp = sign_up_full_name.text.toString()
        var email_sign_up = emailSignUp_et.text.toString()
        var username_ett = username_et.text.toString()
        var phone_ett = phone_et.text.toString()
        var password = password_et.text.toString()

        Validations.required(
            fullnameSignUp,
            nameSignUp
        )
        Validations.Email(
            email_sign_up,
            emailSignUp_text
        )
        Validations.required(
            username_ett,
            username_text
        )
        Validations.CheckPhoneNumber(
            phone_ett,
            phone_text
        )
        Validations.Password(password, password_text)
    }
}
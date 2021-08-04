package com.example.myapplication.Activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.RelativeLayout
import android.widget.TextView
import com.example.myapplication.Fragments.HomeFragment
import com.example.myapplication.R

class ResetPasswordActivity : AppCompatActivity() {

    lateinit var new_password:EditText
    lateinit var layout_submitt:RelativeLayout
    lateinit var background: RelativeLayout
    lateinit var error_text: TextView

    lateinit var re_enter_passeord:EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reset_password)
         layout_submitt=findViewById(R.id.layout_submittt)
        background = findViewById(R.id.forget_back_error)
        error_text = findViewById(R.id.forget_text_error)
        new_password=findViewById(R.id.new_password)
        re_enter_passeord=findViewById(R.id.Re_enter_password)
        layout_submitt.setOnClickListener {
            var new_pass = new_password.text.toString()
            var re_enter_pass = re_enter_passeord.text.toString()
            if ( new_pass.length>=10 ||  re_enter_pass.length>=10) {
                background.setBackgroundResource(R.drawable.background_error)
                error_text.setText("password should not be less than 10 character")
            }
            else if(!new_pass.equals(re_enter_pass)){
                background.setBackgroundResource(R.drawable.background_error)
                error_text.setText("new pass and re-enter password not equal")
            }
            else {
                error_text.setText("")
                background.setBackgroundResource(R.drawable.drawable_back)
                supportFragmentManager.beginTransaction().replace(
                    R.id.linear_layout,
                    HomeFragment()
                ).commit()

            }
        }
    }
}
package com.example.myapplication.Activities

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import com.example.myapplication.Fragments.HomeFragment
import com.example.myapplication.LoginActivity
import com.example.myapplication.R
import org.w3c.dom.Text

class ResetPasswordActivity : AppCompatActivity() {

    lateinit var new_password:EditText
    lateinit var layout_submitt:LinearLayout
    lateinit var background: RelativeLayout
    lateinit var error_text: TextView
lateinit var resetPasswordErrText:TextView
lateinit var reEnterPassword:TextView
    lateinit var re_enter_passeord:EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reset_password)
        if (Build.VERSION.SDK_INT >= 21) {
            val window = window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.statusBarColor = resources.getColor(R.color.black)
        }


         layout_submitt=findViewById(R.id.layout_submittt)
        background = findViewById(R.id.forget_back_error)
        error_text = findViewById(R.id.forget_text_error)
        new_password=findViewById(R.id.new_password)
        re_enter_passeord=findViewById(R.id.Re_enter_password)
        resetPasswordErrText=findViewById(R.id.reset_Password_errtext)
        reEnterPassword=findViewById(R.id.reset_re_neterPassword_errtext)


        layout_submitt.setOnClickListener {
            var new_pass = new_password.text.toString()
            var re_enter_pass = re_enter_passeord.text.toString()
            if (new_pass.length <6) {


                background.setBackgroundResource(R.drawable.background_error)
                error_text.setText("*Please enter new password more than 6-digits.")
                resetPasswordErrText.setText("*Please enter new password more than 6-digits.")
                resetPasswordErrText.visibility = View.VISIBLE
                background.visibility = View.GONE
            }
            else if(!new_pass.equals(re_enter_pass)){

                background.setBackgroundResource(R.drawable.background_error)
                error_text.setText("*Re-enter password is not equal to new password.")
                reEnterPassword.setText("*Re-enter password is not equal to new password.")
                reEnterPassword.visibility = View.VISIBLE
                background.visibility = View.GONE

            }
            else {
                error_text.setText("")
                resetPasswordErrText.setText("")
                reEnterPassword.setText("")
                background.setBackgroundResource(R.drawable.drawable_back)
                val i = Intent(this,LoginActivity::class.java)
                startActivity(i)
                background.visibility = View.GONE
                resetPasswordErrText.visibility = View.GONE
                reEnterPassword.visibility = View.GONE


            }
        }
    }
}
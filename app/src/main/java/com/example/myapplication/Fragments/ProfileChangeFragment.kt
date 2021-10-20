package com.example.myapplication.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import com.example.myapplication.*
import com.example.myapplication.ValidationExt.Validations


class ProfileChangeFragment : Fragment() {
    lateinit var cameraProfileimg: ImageView
    lateinit var fullNameProfileEt: EditText
    lateinit var nameProfileText: TextView
    lateinit var usernameProfileEt: EditText
    lateinit var usernameProfileText: TextView
    lateinit var emailProfileEt: EditText
    lateinit var emailProfileText: TextView
    lateinit var phoneNumberProfileEt: EditText
    lateinit var phoneNumberProfiletext: TextView
    lateinit var layoutButoonSaveChanges: RelativeLayout
    lateinit var backButton:ImageView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var v = inflater.inflate(R.layout.fragment_profile_change, container, false)

        fullNameProfileEt = v.findViewById(R.id.fullname_profile_et)
        nameProfileText = v.findViewById(R.id.name_profile_text)
        usernameProfileEt=v.findViewById(R.id.username_profile_et)
        usernameProfileText=v.findViewById(R.id.username_profile_text)
        emailProfileEt=v.findViewById(R.id.email_profile_etext)
        emailProfileText=v.findViewById(R.id.email_profile_text)
        phoneNumberProfileEt=v.findViewById(R.id.phonenumber_profile_et)
        phoneNumberProfiletext=v.findViewById(R.id.phone_profile_text)
        backButton=v.findViewById(R.id.back_arrow_profile_change)
        backButton.setOnClickListener{
            getFragmentManager()?.beginTransaction()?.replace(
                R.id.linear_layout,
                ProfileFragment()
            )
                ?.commit()
        }

        layoutButoonSaveChanges=v.findViewById(R.id.layout_butoon_svae_changes)


        layoutButoonSaveChanges.setOnClickListener{

        }
        cameraProfileimg = v.findViewById(R.id.img_camera_profile)
        cameraProfileimg.setOnClickListener {
            var bottomsheet =
                bottomSheetDialog()
            fragmentManager?.let { it1 -> bottomsheet.show(it1, "bottomsheet") }
        }

        layoutButoonSaveChanges.setOnClickListener {
            CheckValidations()

        }
        return v
    }

    fun CheckValidations() {
        var fullName = fullNameProfileEt.text.toString()
        var userName= usernameProfileEt.text.toString()
        var email = emailProfileEt.text.toString().trim()
        var phoneNumber=phoneNumberProfileEt.text.toString()

      if(  Validations.required(fullName, nameProfileText)&&
        Validations.required(userName,usernameProfileText
        )&&
        Validations.Email(email, emailProfileText)&&
        Validations.CheckPhoneNumber(
            phoneNumber,
            phoneNumberProfiletext
        )){
          getFragmentManager()?.beginTransaction()?.replace(
              R.id.linear_layout,
              EditProfileFragment()
          )
              ?.commit()
      }
    }

}
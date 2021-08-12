package com.example.myapplication.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import com.example.myapplication.R


class secondFragment : Fragment() {
lateinit var man:ImageView
    lateinit var textSearch1: LinearLayout
    lateinit var textSearch2: LinearLayout
    lateinit var textSearch3: LinearLayout

    lateinit var textSearch4: LinearLayout
    lateinit var textSearch6: LinearLayout

    lateinit var textSearch5: LinearLayout
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var v= inflater.inflate(R.layout.fragment_second, container, false)
        man=v.findViewById(R.id.user)

        textSearch1=v.findViewById(R.id.textsearch1)
        textSearch2=v.findViewById(R.id.textsearch2)

        textSearch3=v.findViewById(R.id.textsearch3)
        textSearch4=v.findViewById(R.id.textsearch4)

        textSearch5=v.findViewById(R.id.textsearch5)
        textSearch6=v.findViewById(R.id.textsearch6)

        textSearch1.setOnClickListener{
            textSearch1.setBackgroundResource(R.drawable.drawable_chat)
            textSearch2.setBackgroundResource(R.drawable.background_edit_text)
            textSearch3.setBackgroundResource(R.drawable.background_edit_text)
            textSearch4.setBackgroundResource(R.drawable.background_edit_text)
            textSearch5.setBackgroundResource(R.drawable.background_edit_text)
            textSearch6.setBackgroundResource(R.drawable.background_edit_text)
        }
        textSearch3.setOnClickListener{
            textSearch3.setBackgroundResource(R.drawable.drawable_chat)
            textSearch2.setBackgroundResource(R.drawable.background_edit_text)
            textSearch1.setBackgroundResource(R.drawable.background_edit_text)
            textSearch4.setBackgroundResource(R.drawable.background_edit_text)
            textSearch5.setBackgroundResource(R.drawable.background_edit_text)
            textSearch6.setBackgroundResource(R.drawable.background_edit_text)
        }
        textSearch4.setOnClickListener{
            textSearch4.setBackgroundResource(R.drawable.drawable_chat)
            textSearch2.setBackgroundResource(R.drawable.background_edit_text)
            textSearch3.setBackgroundResource(R.drawable.background_edit_text)
            textSearch1.setBackgroundResource(R.drawable.background_edit_text)
            textSearch5.setBackgroundResource(R.drawable.background_edit_text)
            textSearch6.setBackgroundResource(R.drawable.background_edit_text)
        }
        textSearch5.setOnClickListener{
            textSearch5.setBackgroundResource(R.drawable.drawable_chat)
            textSearch2.setBackgroundResource(R.drawable.background_edit_text)
            textSearch3.setBackgroundResource(R.drawable.background_edit_text)
            textSearch4.setBackgroundResource(R.drawable.background_edit_text)
            textSearch1.setBackgroundResource(R.drawable.background_edit_text)
            textSearch6.setBackgroundResource(R.drawable.background_edit_text)
        }
        textSearch6.setOnClickListener{
            textSearch6.setBackgroundResource(R.drawable.drawable_chat)
            textSearch2.setBackgroundResource(R.drawable.background_edit_text)
            textSearch3.setBackgroundResource(R.drawable.background_edit_text)
            textSearch4.setBackgroundResource(R.drawable.background_edit_text)
            textSearch1.setBackgroundResource(R.drawable.background_edit_text)
            textSearch5.setBackgroundResource(R.drawable.background_edit_text)
        }
        textSearch2.setOnClickListener{
            textSearch2.setBackgroundResource(R.drawable.drawable_chat)
            textSearch1.setBackgroundResource(R.drawable.background_edit_text)
            textSearch3.setBackgroundResource(R.drawable.background_edit_text)
            textSearch4.setBackgroundResource(R.drawable.background_edit_text)
            textSearch5.setBackgroundResource(R.drawable.background_edit_text)
            textSearch6.setBackgroundResource(R.drawable.background_edit_text)



        }

        man.setOnClickListener{
            getFragmentManager()?.beginTransaction()?.replace(
                R.id.linear_layout,
                HomeFragment()
            )
                ?.commit()
        }
        return v
    }

}
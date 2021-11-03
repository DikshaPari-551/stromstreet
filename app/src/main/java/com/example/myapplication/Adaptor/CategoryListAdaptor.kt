package com.example.myapplication.Adaptor

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.customclickListner.FilterCustomListener
import com.example.myapplication.entity.Response.CategoryResult


class CategoryListAdaptor(
    var list: List<CategoryResult>,
    var secondFragment: FilterCustomListener,
    var mContext: Context
) :  RecyclerView.Adapter<CategoryListAdaptor.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.category_list, null)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.radioButton.setText(list.get(position).categoryName)
        holder.radioButton.setOnClickListener{
            secondFragment.filterCustomListener(list.get(position)._id)
        }

    }

    override fun getItemCount(): Int {
        return list.size
    }

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view){
        var radioButton = view.findViewById<RadioButton>(R.id.radio1)
        var radioGroup = view.findViewById<RadioGroup>(R.id.group)

    }
}
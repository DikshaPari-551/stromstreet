package com.stormstreet.myapplication.Adaptor

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.recyclerview.widget.RecyclerView
import com.stormstreet.myapplication.R
import com.stormstreet.myapplication.customclickListner.FilterCustomListener
import com.stormstreet.myapplication.entity.Response.CategoryResult


class CategoryListAdaptor(
    var list: ArrayList<CategoryResult>,
    var secondFragment: FilterCustomListener,
    var mContext: Context
) :  RecyclerView.Adapter<CategoryListAdaptor.MyViewHolder>() {
    var selectPosition : Int = 0
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.category_list, null)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
//        if(position == selectPosition) {
//            holder.radioButton.isChecked = true
//        }
//        else {
//            holder.radioButton.isChecked = false
//        }
        if( list.get(position).flag)
        {
            holder.radioButton.isChecked=true
        }
        else
        {
            holder.radioButton.isChecked=false
        }
        holder.radioButton.setText(list.get(position).categoryName)

        holder.radioButton.setOnClickListener{
            secondFragment.filterCustomListener(list)
            selectPosition = position
            if(!list.get(position).flag)
            {
                list.get(position).flag=true
            }
            else
            {
                list.get(position).flag=false
            }
            notifyDataSetChanged()
        }

    }

    override fun getItemCount(): Int {
        return list.size
    }

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view){
        var radioButton = view.findViewById<RadioButton>(R.id.radio1)


    }

    override fun getItemViewType(position: Int): Int {
        return super.getItemViewType(position)
    }
}
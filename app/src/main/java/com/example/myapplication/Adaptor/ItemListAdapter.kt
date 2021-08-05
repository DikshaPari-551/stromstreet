package com.example.myapplication.Adaptor

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.myapplication.R
import java.util.*

class ItemListAdapter : RecyclerView.Adapter<ItemListAdapter.ViewHolder>() {
    private val selectedDataList = ArrayList<String>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.list_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val dataUri = selectedDataList[position]
        holder.imageUri.text = dataUri
        Glide.with(holder.itemView).load(dataUri).thumbnail(0.1f)
            .apply(RequestOptions().centerCrop()).into(holder.image)
        holder.clearButton.setOnClickListener { view: View? ->
            selectedDataList.remove(dataUri)
            notifyDataSetChanged()
        }
    }

    fun addAllData(selectedData: List<String>) {
        selectedDataList.addAll(selectedData)
        notifyDataSetChanged()
    }

    fun setAllData(selectedData: List<String>) {
        selectedDataList.clear()
        addAllData(selectedData)
    }

    fun getSelectedDataList(): List<String> {
        return Collections.unmodifiableList(selectedDataList)
    }

    override fun getItemCount(): Int {
        return selectedDataList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image: ImageView
        val imageUri: TextView
        val clearButton: ImageButton

        init {
            image = itemView.findViewById(R.id.image)
            imageUri = itemView.findViewById(R.id.imageUri)
            clearButton = itemView.findViewById(R.id.clearButton)
        }
    }
}
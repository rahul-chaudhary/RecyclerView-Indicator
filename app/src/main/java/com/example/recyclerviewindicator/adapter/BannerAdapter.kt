package com.example.recyclerviewindicator.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclerviewindicator.R

class BannerAdapter(private val imagesItem: List<Int>): RecyclerView.Adapter<BannerAdapter.ImagesViewHolder>() {

    inner class ImagesViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val imgView: ImageView = itemView.findViewById(R.id.banner_image)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImagesViewHolder {
        val view = View.inflate(parent.context, R.layout.banner_rv_item, null)
//        return ImagesViewHolder(View.inflate(parent.context, R.layout.banner_rv_item, null))
        return ImagesViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.banner_rv_item, parent, false)
        )
    }


    override fun getItemCount(): Int {
        return imagesItem.size
    }

    override fun onBindViewHolder(holder: ImagesViewHolder, position: Int) {
        val currentItem = imagesItem[position%itemCount]
        holder.imgView.setImageResource(currentItem)
        holder.imgView.setOnClickListener {
            // Handle click event
            Toast.makeText(holder.imgView.context, "Image $position clicked", Toast.LENGTH_SHORT).show()
        }
    }
}
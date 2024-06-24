package com.example.recyclerviewindicator.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclerviewindicator.R
import com.google.android.material.progressindicator.LinearProgressIndicator

class BannerAdapter(
    private val imagesItem: List<Int>,
    private val onProgressUpdate: (Int) -> Unit,
    private val pauseAutoScroll: (Boolean) -> Unit,
    ): RecyclerView.Adapter<BannerAdapter.ImagesViewHolder>() {

        var pauseAutoSc = false

    inner class ImagesViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val imgView: ImageView = itemView.findViewById(R.id.banner_image)
//        val progressBar: LinearProgressIndicator = itemView.findViewById(R.id.linearProgressIndicator)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImagesViewHolder {
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
        val pos = position%itemCount
        holder.imgView.setImageResource(currentItem)
        holder.imgView.setOnClickListener {
            // Handle click event
            Toast.makeText(holder.imgView.context, "Image $position clicked", Toast.LENGTH_SHORT).show()
            pauseAutoSc = !pauseAutoSc
            pauseAutoScroll(pauseAutoSc)
        }

        // Calculate and set the progress percent
        val progressPercent = (pos.toFloat() / (itemCount - 1) * 100).toInt()
        Log.i("BannerAdapter", "Progress percent: $progressPercent")
        onProgressUpdate(progressPercent)
    }

}
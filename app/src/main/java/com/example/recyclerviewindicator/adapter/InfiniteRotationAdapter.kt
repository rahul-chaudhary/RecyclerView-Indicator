package com.example.recyclerviewindicator.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclerviewindicator.R

/**
 * Created by tomoaki on 2017/08/13.
 */
internal class InfiniteRotationAdapter(val imagesItem: List<Int>) : RecyclerView.Adapter<InfiniteRotationAdapter.ItemViewHolder>() {

    private val list: List<Int> = listOf(imagesItem.last()) + imagesItem + listOf(imagesItem.first())


    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
//        val currentItem = list[position % list.size]
        holder.imgView.setImageResource(list[position % list.size])
        holder.imgView.setOnClickListener {
            // Handle click event
            Toast.makeText(holder.imgView.context, "Image $position clicked", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.banner_rv_item, parent, false)
        )
    }

    override fun getItemCount() = list.size

    internal class ItemViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val imgView: ImageView = itemView.findViewById(R.id.banner_image)
    }
}
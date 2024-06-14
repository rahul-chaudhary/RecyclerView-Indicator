package com.example.recyclerviewindicator

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclerviewindicator.DummyData.fetchImages
import com.example.recyclerviewindicator.LoopingLayoutManager.LoopingLayoutManager
import com.example.recyclerviewindicator.adapter.BannerAdapter
import com.example.recyclerviewindicator.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val itemList = fetchImages()
        //layout manager
        val linearLayoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.bannerRv.layoutManager = linearLayoutManager
//      binding.bannerRv.layoutManager = LoopingLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        //adapter
        binding.bannerRv.adapter = BannerAdapter(itemList)

        //snap helper
      val snapHostHelper = LinearSnapHelper()
      snapHostHelper.attachToRecyclerView(binding.bannerRv)

        //scroll listener
        binding.bannerRv.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val firstItemVisible = linearLayoutManager.findFirstVisibleItemPosition()
                if (firstItemVisible != 0 && firstItemVisible % itemList.size == 0) {
                    recyclerView.layoutManager?.scrollToPosition(0)
                }
            }
        })



    }



}
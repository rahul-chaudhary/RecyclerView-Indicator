package com.example.recyclerviewindicator

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
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

        binding.bannerRv.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
//      binding.bannerRv.layoutManager = LoopingLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.bannerRv.adapter = BannerAdapter(fetchImages())
//      val snapHostHelper = LinearSnapHelper()
//      snapHostHelper.attachToRecyclerView(binding.bannerRv)


    }



}
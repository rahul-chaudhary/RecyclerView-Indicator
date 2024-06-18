package com.example.recyclerviewindicator

import android.content.res.Resources
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import com.example.recyclerviewindicator.DummyData.fetchImages
import com.example.recyclerviewindicator.adapter.BannerAdapter
import com.example.recyclerviewindicator.archyLayoutManager.ArcLayoutManager
import com.example.recyclerviewindicator.customLayoutManager.CustomLayoutManager
import com.example.recyclerviewindicator.databinding.ActivityMainBinding
import com.google.android.material.progressindicator.LinearProgressIndicator

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var layoutManager: ArcLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val screenWidth = Resources.getSystem().displayMetrics.widthPixels
        val viewWidth = screenWidth / 2
        val viewHeight = (1.25f * viewWidth).toInt()

        //layout manager
        binding.bannerRv.layoutManager = CustomLayoutManager()
//         binding.bannerRv.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        //binding.bannerRv.layoutManager = LoopingLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
//        binding.bannerRv.layoutManager = ArcLayoutManager(resources, screenWidth, viewWidth, viewHeight).apply {
//            layoutManager = this
//        }

        //adapter
        binding.bannerRv.adapter = BannerAdapter(fetchImages())

        //snap helper
        val snapHostHelper = LinearSnapHelper()
        snapHostHelper.attachToRecyclerView(binding.bannerRv)



    }


}
package com.example.recyclerviewindicator

import android.content.res.Resources
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclerviewindicator.DummyData.fetchImages
import com.example.recyclerviewindicator.adapter.BannerAdapter
import com.example.recyclerviewindicator.archyLayoutManager.ArcLayoutManager
import com.example.recyclerviewindicator.customLayoutManager.CustomLayoutManager
import com.example.recyclerviewindicator.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpBannerRV()
    }

    private fun setUpBannerRV() {
        val customLayoutManager = CustomLayoutManager()
        binding.bannerRv.layoutManager = customLayoutManager

        // Adapter
        binding.bannerRv.adapter = BannerAdapter(fetchImages()) { progressPercent ->
            if (progressPercent > 100)
                updateProgressBar(progressPercent)
        }

        // Snap helper
        val snapHostHelper = LinearSnapHelper()
        snapHostHelper.attachToRecyclerView(binding.bannerRv)

        // Scroll listener
        binding.bannerRv.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    val layoutManager = recyclerView.layoutManager as CustomLayoutManager
                    val visiblePosition = layoutManager.getFirstVisibleItemPosition() % layoutManager.itemCount
                    val progressPercent =
                        ((visiblePosition.toFloat()) / (layoutManager.itemCount - 1) * 100).toInt()
                    updateProgressBar(progressPercent)
                    Log.d(
                        "MainActivity Progress",
                        "Visible Position: $visiblePosition\n" +
                                "Item Count: ${layoutManager.itemCount}\n" +
                                "Progress Percent: $progressPercent\n"
                    )
                }
            }
        })

    }

    private fun updateProgressBar(progressPercent: Int) {
        binding.linearProgressIndicator.progress = progressPercent
    }

}
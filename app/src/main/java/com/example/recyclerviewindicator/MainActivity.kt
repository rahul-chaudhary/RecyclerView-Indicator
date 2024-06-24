package com.example.recyclerviewindicator

import android.content.res.Resources
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.MotionEvent
import android.view.animation.LinearInterpolator
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclerviewindicator.DummyData.fetchImages
import com.example.recyclerviewindicator.adapter.BannerAdapter
import com.example.recyclerviewindicator.customLayoutManager.CustomLayoutManager
import com.example.recyclerviewindicator.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val handler = Handler(Looper.getMainLooper())
    private var pauseAutoScroll = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpBannerRV()
        bannerRVAutoScroll()
    }

    private fun bannerRVAutoScroll() {
        val screenWidth = Resources.getSystem().displayMetrics.xdpi.toInt()

        val scrollRunnable = object : Runnable {
            override fun run() {
                if (!pauseAutoScroll) {
                    binding.bannerRv.smoothScrollBy(screenWidth * 2, 0, LinearInterpolator())
                }
                handler.postDelayed(this, 3000)  // Schedule the next scroll
            }
        }

        handler.postDelayed(scrollRunnable, 3000)  // Start the first scroll after 3000 ms

        binding.bannerRv.setOnTouchListener { _, event ->

            when (event.action) {
                MotionEvent.ACTION_DOWN, MotionEvent.ACTION_MOVE -> {
                    pauseAutoScroll = true
                    handler.removeCallbacks(scrollRunnable)
                }
                MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                    pauseAutoScroll = false
                    handler.postDelayed(scrollRunnable, 3000)  // Resume scrolling after 3000 ms
                }
            }
            false
        }
    }

    private fun setUpBannerRV() {
        val customLayoutManager = CustomLayoutManager()
        binding.bannerRv.layoutManager = customLayoutManager

        // Adapter
        binding.bannerRv.adapter = BannerAdapter(
            fetchImages(),
            { progressPercent -> updateProgressBar(progressPercent) },
            { pauseAutoScroll -> this.pauseAutoScroll = pauseAutoScroll }
        )

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
                    val progressPercent = ((visiblePosition.toFloat()) / (layoutManager.itemCount - 1) * 100).toInt()
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

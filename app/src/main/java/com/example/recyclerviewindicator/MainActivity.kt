package com.example.recyclerviewindicator

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclerviewindicator.adapter.BannerAdapter
import com.example.recyclerviewindicator.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val CLONE_COUNT = 3 // Number of clones to add on each side

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Prepare data with clones
        val imageIds = prepareData(fetchImages())

        // Setup RecyclerView
        binding.bannerRv.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.bannerRv.adapter = BannerAdapter(imageIds)

        // Add SnapHelper for snapping to center
        val snapHelper = LinearSnapHelper()
        snapHelper.attachToRecyclerView(binding.bannerRv)

        // Add scroll listener for infinite scrolling effect
        binding.bannerRv.addOnScrollListener(InfiniteScrollListener(CLONE_COUNT, imageIds.size))
    }

    private fun prepareData(originalData: List<Int>): List<Int> {
        val clonedData = mutableListOf<Int>()
        repeat(CLONE_COUNT) { clonedData.add(originalData.last()) } // Add clones from the end
        clonedData.addAll(originalData) // Add original data
        repeat(CLONE_COUNT) { clonedData.add(originalData.first()) } // Add clones from the beginning
        return clonedData.toList()
    }

    private fun fetchImages(): List<Int> {
        // Your logic to fetch image resource IDs
        return listOf(
            R.drawable.banner1,
            R.drawable.banner2,
            R.drawable.banner3,
            R.drawable.banner4
        )
    }

    private inner class InfiniteScrollListener(
        private val cloneCount: Int,
        private val realItemCount: Int
    ) : RecyclerView.OnScrollListener() {

        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)

            val layoutManager = recyclerView.layoutManager as LinearLayoutManager
            val firstVisibleItem = layoutManager.findFirstVisibleItemPosition()
            val lastVisibleItem = layoutManager.findLastVisibleItemPosition()

            if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                if (firstVisibleItem < cloneCount) {
                    // Scrolled to the left, adjust position to show first real item
                    recyclerView.scrollBy((cloneCount - firstVisibleItem) * layoutManager.itemCount, 0)
                } else if (lastVisibleItem > realItemCount - 1 + cloneCount) {
                    // Scrolled to the right, adjust position to show last real item
                    recyclerView.scrollBy((realItemCount - 1 - lastVisibleItem - cloneCount) * layoutManager.itemCount, 0)
                }
            }
        }
    }
}

package com.example.recyclerviewindicator.listener

import android.widget.Adapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class CircularScrollListener(private val itemCount: Int) : RecyclerView.OnScrollListener() {

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        val layoutManager = recyclerView.layoutManager as LinearLayoutManager
        val firstItemVisible: Int = layoutManager.findFirstVisibleItemPosition()

        // Ensure the total item count is not zero to avoid division by zero
        if (itemCount == 0) return

        if (firstItemVisible == 0) {
            // Scroll to the last item if the first item is visible
            layoutManager.scrollToPositionWithOffset(itemCount, -recyclerView.computeHorizontalScrollOffset())
        } else if (firstItemVisible >= itemCount + 1) {
            // Scroll to the second item if beyond the last item
            layoutManager.scrollToPositionWithOffset(1, 0)
        }
    }
}

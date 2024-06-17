package com.example.recyclerviewindicator.customLayoutManager

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.HORIZONTAL
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import kotlin.math.floor

class CustomLayoutManager(private val viewWidth: Int,
                          private val screenWidth: Int,
                          private val viewHeight: Int) : RecyclerView.LayoutManager() {

    private var horizontalScrollOffset = viewWidth /2
    override fun generateDefaultLayoutParams(): RecyclerView.LayoutParams =
        RecyclerView.LayoutParams(
            RecyclerView.LayoutParams.MATCH_PARENT,
            RecyclerView.LayoutParams.MATCH_PARENT
        )

    override fun onLayoutChildren(recycler: RecyclerView.Recycler, state: RecyclerView.State) {
        fill(recycler, state)
    }

    private fun fill(recycler: RecyclerView.Recycler, state: RecyclerView.State) {
        detachAndScrapAttachedViews(recycler)

        //looping
        val firstVisiblePos = floor(horizontalScrollOffset.toDouble() / viewWidth.toDouble()).toInt()
        val lastVisiblePos = (horizontalScrollOffset + screenWidth) / viewWidth

        for(index in firstVisiblePos..lastVisiblePos) {
            var rvIndex = index % itemCount
            if(rvIndex < 0) {
                rvIndex += itemCount
            }
            val view = recycler.getViewForPosition(rvIndex)
            addView(view)

            layoutChildView(index, viewWidth, view)
        }
        val scrapListCopy = recycler.scrapList.toList()
        scrapListCopy.forEach {
            recycler.recycleView(it.itemView)
        }
    }

//    private fun layoutChildView(index: Int, viewWidthSpacing: Int, view: View) {
//        val left = index * viewWidthSpacing - horizontalScrollOffset
//        val right =  left + viewWidth
//        val top = 0;
//        val bottom = top + viewHeight
//        // Measure
//        measureChild(view, viewWidth, viewHeight + 200)
//        // Layout
//        layoutDecorated(view, left, top, right, bottom)
//    }
private fun layoutChildView(index: Int, viewWidthSpacing: Int, view: View) {
    val left = index * viewWidthSpacing - horizontalScrollOffset

    // Check if view is within visible area
    if (left + viewWidth >= 0 && left < screenWidth) {
        val right = left + viewWidth
        val top = 0;
        val bottom = top + viewHeight
        // Measure (optional, adjust based on your needs)
        measureChild(view, viewWidth, viewHeight)
        // Layout
        layoutDecorated(view, left, top, right, bottom)
    }
}

    override fun canScrollHorizontally(): Boolean  = true

    override fun scrollHorizontallyBy(
        dx: Int,
        recycler: RecyclerView.Recycler,
        state: RecyclerView.State
    ): Int {
        horizontalScrollOffset += dx
        fill(recycler, state)
        return dx
    }
}
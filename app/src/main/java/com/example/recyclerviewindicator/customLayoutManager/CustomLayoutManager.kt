package com.example.recyclerviewindicator.customLayoutManager

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.HORIZONTAL
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import kotlin.math.ceil
import kotlin.math.floor
import kotlin.math.max
import kotlin.math.min

class CustomLayoutManager : RecyclerView.LayoutManager() {

    private var horizontalScrollOffset = 0

    override fun generateDefaultLayoutParams(): RecyclerView.LayoutParams =
        RecyclerView.LayoutParams(
            RecyclerView.LayoutParams.MATCH_PARENT,
            RecyclerView.LayoutParams.MATCH_PARENT
        )

    override fun onLayoutChildren(recycler: RecyclerView.Recycler, state: RecyclerView.State?) {
        fill(recycler)
    }

    private fun fill(recycler: RecyclerView.Recycler) {
        if (itemCount == 0) return

        detachAndScrapAttachedViews(recycler)

        val firstVisibleItem = getFirstVisibleItemPosition()
        val lastVisibleItem = getLastVisibleItemPosition()

        for (i in firstVisibleItem..lastVisibleItem) {
            val actualPosition = i % itemCount
            val view = recycler.getViewForPosition(actualPosition)
            addView(view)

            val layoutParams = view.layoutParams as RecyclerView.LayoutParams
            layoutParams.width = width
            layoutParams.height = height
            view.layoutParams = layoutParams

            val left = (i * width - horizontalScrollOffset + itemCount * width) % (itemCount * width)
            val right = left + width
            val top = 0
            val bottom = top + height

            measureChild(view, width, height)
            layoutDecorated(view, left, top, right, bottom)
        }

        // Recycle views that are no longer visible
        val scrapListCopy = recycler.scrapList.toList()
        scrapListCopy.forEach {
            recycler.recycleView(it.itemView)
        }
    }

    override fun canScrollHorizontally(): Boolean = true

    override fun scrollHorizontallyBy(
        dx: Int,
        recycler: RecyclerView.Recycler?,
        state: RecyclerView.State?
    ): Int {
        if (itemCount == 0) return 0

        val totalWidth = width * itemCount

        horizontalScrollOffset += dx

        // Adjust the offset to loop correctly
        if (horizontalScrollOffset < 0) {
            horizontalScrollOffset += totalWidth
        } else if (horizontalScrollOffset >= totalWidth) {
            horizontalScrollOffset -= totalWidth
        }

        // Offset children views
        offsetChildrenHorizontal(-dx)

        fill(recycler!!)

        return dx
    }

    private fun getFirstVisibleItemPosition(): Int {
        return floor(horizontalScrollOffset.toDouble() / width).toInt()
    }

    private fun getLastVisibleItemPosition(): Int {
        return ceil((horizontalScrollOffset + width).toDouble() / width).toInt() - 1
    }
}

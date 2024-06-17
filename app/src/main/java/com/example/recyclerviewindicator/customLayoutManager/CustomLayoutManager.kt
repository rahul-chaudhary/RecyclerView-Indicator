package com.example.recyclerviewindicator.customLayoutManager

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.HORIZONTAL
import androidx.recyclerview.widget.RecyclerView.LayoutManager
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
        if(itemCount == 0) return
        detachAndScrapAttachedViews(recycler)

        val firstVisibleItem = getFirstVisibleItemPosition()
        val lastVisibleItem = getLastVisibleItemPosition()

        for(pos in firstVisibleItem .. lastVisibleItem) {
            val view = recycler.getViewForPosition(pos)
            addView(view)

            val layoutParams = view.layoutParams as RecyclerView.LayoutParams
            layoutParams.width = width
            layoutParams.height = height
            view.layoutParams = layoutParams

            var left = pos * width - horizontalScrollOffset //pos * width - horizontalScrollOffset
            var right = left + width // left + width
            var top = 0
            var bottom = top + height

            measureChild(view, width, height)
            layoutDecorated(view, left, top, right, bottom)
        }
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

        val delta = if (dx + horizontalScrollOffset < 0) {
            -horizontalScrollOffset
        } else if (dx + horizontalScrollOffset > width * (itemCount - 1)) {
            width * (itemCount - 1) - horizontalScrollOffset
        } else {
            dx
        }

        horizontalScrollOffset += delta

        offsetChildrenHorizontal(-delta)

        fill(recycler!!)

        return delta
    }

    private fun getFirstVisibleItemPosition(): Int {
        val firstView = getChildAt(0) ?: return 0
        return max(0, getDecoratedLeft(firstView) / width)
    }

    private fun getLastVisibleItemPosition(): Int {
        val lastView = getChildAt(childCount - 1) ?: return itemCount - 1
        return min(itemCount - 1, (getDecoratedRight(lastView) - width) / width)
    }


}
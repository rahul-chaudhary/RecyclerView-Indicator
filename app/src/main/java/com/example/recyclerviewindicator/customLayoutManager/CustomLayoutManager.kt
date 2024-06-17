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
        for(pos in 0 until itemCount) {
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
        if(itemCount == 0) return 0
        val lastOffset = horizontalScrollOffset
        val firstItem = requireNotNull(getChildAt(0))
        val lastItem = requireNotNull(getChildAt(itemCount - 1))
        val startOfFirstItem = getDecoratedLeft(firstItem)
        val endOfLastItem = getDecoratedRight(lastItem)
        horizontalScrollOffset = min(max(startOfFirstItem, horizontalScrollOffset + dx), horizontalScrollOffset + endOfLastItem - width)
        return lastOffset - horizontalScrollOffset

    }



}
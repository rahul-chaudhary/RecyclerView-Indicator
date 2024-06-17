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
        if(itemCount == 0) return

        val view = recycler.getViewForPosition(0)
        addView(view)

        val layoutParams = view.layoutParams as RecyclerView.LayoutParams
        layoutParams.width = width
        layoutParams.height = height
        view.layoutParams = layoutParams

        var left = 0
        var right = left + width
        var top = 0
        var bottom = top + height

        measureChild(view, width, width)
        layoutDecorated(view, left, top, right, bottom)

    }

    override fun canScrollHorizontally(): Boolean = true
}
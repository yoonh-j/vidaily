package com.yoond.vidaily.decorators

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class ListDecoration(
    private val margin: Int,
    private val isVertical: Boolean
): RecyclerView.ItemDecoration() {
    var pos: Int = 0

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        pos = parent.getChildAdapterPosition(view)

        when (isVertical) {
            true -> {
                outRect.bottom =
                    if (pos == state.itemCount - 1) 0
                    else margin
            }
            else -> {
                if (pos == state.itemCount - 1) {
                    outRect.right = margin
                }
                outRect.left = margin
            }
        }
    }
}
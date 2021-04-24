package com.l2hyunwoo.android.presentation.util

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class VerticaltemDecorator(private val padding: Int) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        when(parent.getChildAdapterPosition(view)) {
            0 -> {
                with(outRect) {
                    top = padding
                    bottom = padding
                }
            }
            else -> outRect.bottom = padding
        }
    }
}
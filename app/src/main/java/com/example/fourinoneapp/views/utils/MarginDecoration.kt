package com.example.fourinoneapp.views.utils

import android.content.Context
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.fourinoneapp.R

class MarginDecoration(context: Context) : RecyclerView.ItemDecoration() {
    private val space: Int

    init {
        space = context.resources.getDimensionPixelSize(R.dimen.item_margin)
    }

    override fun getItemOffsets(
            outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        outRect.left = space
        outRect.right = space
        outRect.bottom = space
        outRect.top = space
    }
}
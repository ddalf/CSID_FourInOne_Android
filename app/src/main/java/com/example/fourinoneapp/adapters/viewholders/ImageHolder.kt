package com.example.fourinoneapp.adapters.viewholders

import androidx.recyclerview.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import kotlinx.android.synthetic.main.picture_browser_pager.view.*

class ImageHolder internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var picture: ImageView

    init {
        picture = itemView.image
    }
}

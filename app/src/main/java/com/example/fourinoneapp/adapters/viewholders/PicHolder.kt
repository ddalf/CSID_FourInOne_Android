package com.example.fourinoneapp.adapters.viewholders

import androidx.recyclerview.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import com.example.fourinoneapp.R

class PicHolder internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var picture: ImageView

    init {
        picture = itemView.findViewById(R.id.image)
    }
}

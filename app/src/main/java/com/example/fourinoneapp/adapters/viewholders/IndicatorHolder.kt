package com.example.fourinoneapp.adapters.viewholders

import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import kotlinx.android.synthetic.main.indicator_holder.view.*

class IndicatorHolder internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {

    var image: ImageView
    private val card: CardView
    internal var positionController: View

    init {
        image = itemView.imageIndicator
        card = itemView.indicatorCard
        positionController = itemView.activeImage
    }
}

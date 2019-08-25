package com.example.fourinoneapp.adapters.viewholders

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_image_export.view.*

class ImageExportHolder internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var exportImgIV: ImageView
    var exportTxtTV : TextView
    var exportTxtCV : CardView

    init {
        exportImgIV = itemView.exportImgIV
        exportTxtTV = itemView.exportTxtTV
        exportTxtCV = itemView.exportTxtCV
    }
}

package com.example.fourinoneapp.adapters

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.ViewCompat.setTransitionName
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.fourinoneapp.R
import com.example.fourinoneapp.adapters.viewholders.ImageExportHolder
import com.example.fourinoneapp.models.ImageExporter
import com.example.fourinoneapp.views.GalleryExportDetailActivity
import java.util.*

class ImageExportAdapter
    (private val pictureList: ArrayList<ImageExporter>, private val pictureContx: Context) : RecyclerView.Adapter<ImageExportHolder>() {

    override fun onCreateViewHolder(container: ViewGroup, position: Int): ImageExportHolder {
        val inflater = LayoutInflater.from(container.context)
        val cell = inflater.inflate(R.layout.item_image_export, container, false)
        return ImageExportHolder(cell)
    }

    override fun onBindViewHolder(holder: ImageExportHolder, position: Int) {

        val image = pictureList[position]
        Log.d("exportadapter", image.imageFacer.picturePath)
        holder.exportTxtTV.text = image.imageTXT

        Glide.with(pictureContx)
            .load(image.imageFacer.picturePath)
            .apply(RequestOptions().centerCrop())
            .into(holder.exportImgIV)
        setTransitionName(holder.exportImgIV, position.toString() + "_image")
        holder.exportImgIV.setOnClickListener {
            val move = Intent(pictureContx, GalleryExportDetailActivity::class.java)
            move.putExtra("exportImg", image)
            pictureContx.startActivity(move)
        }
    }

    override fun getItemCount(): Int {
        return pictureList.size
    }
}

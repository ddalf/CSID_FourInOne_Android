package com.example.fourinoneapp.adapters

import android.content.Context
import android.content.Intent
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
=======
import android.graphics.drawable.Drawable
import android.util.DisplayMetrics
>>>>>>> 6b0a4c1... change layout
=======
>>>>>>> 8d2517b... reset
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
<<<<<<< HEAD
import androidx.core.view.ViewCompat.setTransitionName
import androidx.recyclerview.widget.RecyclerView
<<<<<<< HEAD
=======
import androidx.core.content.ContextCompat

>>>>>>> 6b0a4c1... change layout
=======
>>>>>>> 8d2517b... reset
=======
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.ViewCompat.setTransitionName
import androidx.recyclerview.widget.RecyclerView
>>>>>>> 8d2517be0ed48898de8b0d5bf025cd120bd6dc91
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
        Log.d("exportadapter", image.picturePath)
        holder.exportTxtTV.text = image.imageTXT

        Glide.with(pictureContx)
            .load(image.picturePath)
            .apply(RequestOptions().centerCrop())
            .into(holder.exportImgIV)
        setTransitionName(holder.exportImgIV, position.toString() + "_image")
        holder.exportImgIV.setOnClickListener {
            val move = Intent(pictureContx, GalleryExportDetailActivity::class.java)
            move.putExtra("exportImg", image)
            pictureContx.startActivity(move)
        }
        holder.exportTxtTV.text = image.imageTXT
        holder.exportTxtTV.setOnClickListener {
            val move = Intent(pictureContx, GalleryExportDetailActivity::class.java)
            move.putExtra("exportImg", image)
            pictureContx.startActivity(move)
        }

        val drawable = pictureContx.getDrawable(R.drawable.rounded_imageview)
        holder.exportImgIV.background = drawable
        holder.exportImgIV.clipToOutline = true

        if(image.imageTXT.equals("")){
            val noTxtDrawable = pictureContx.getDrawable(R.drawable.rounded_no_txt)
            holder.exportTxtCV.background = noTxtDrawable
        }
    }

    override fun getItemCount(): Int {
        return pictureList.size
    }
}

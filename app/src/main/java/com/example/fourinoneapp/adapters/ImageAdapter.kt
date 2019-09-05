package com.example.fourinoneapp.adapters.viewholders

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup

import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import java.util.ArrayList
import androidx.core.view.ViewCompat.setTransitionName
import com.example.fourinoneapp.R
import com.example.fourinoneapp.listeners.ImageClickListener
import com.example.fourinoneapp.models.ImageFacer

class ImageAdapter
(private val pictureList: ArrayList<ImageFacer>, private val pictureContx: Context, private val picListerner: ImageClickListener) : RecyclerView.Adapter<ImageHolder>() {

    override fun onCreateViewHolder(container: ViewGroup, position: Int): ImageHolder {
        val inflater = LayoutInflater.from(container.context)
        val cell = inflater.inflate(R.layout.item_image_holder, container, false)
        return ImageHolder(cell)
    }

    override fun onBindViewHolder(holder: ImageHolder, position: Int) {

        val image = pictureList[position]

        Glide.with(pictureContx)
                .load(image.picturePath)
                .apply(RequestOptions().centerCrop())
                .into(holder.picture)

        setTransitionName(holder.picture, position.toString() + "_image")
        holder.picture.setOnClickListener { picListerner.onPicClicked(holder, position, pictureList) }
    }

    override fun getItemCount(): Int {
        return pictureList.size
    }
}

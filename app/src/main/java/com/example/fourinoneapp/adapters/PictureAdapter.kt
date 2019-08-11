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
import com.example.fourinoneapp.listeners.itemClickListener
import com.example.fourinoneapp.viewmodels.pictureFacer

class PictureAdapter
(private val pictureList: ArrayList<pictureFacer>, private val pictureContx: Context, private val picListerner: itemClickListener) : RecyclerView.Adapter<PicHolder>() {

    override fun onCreateViewHolder(container: ViewGroup, position: Int): PicHolder {
        val inflater = LayoutInflater.from(container.context)
        val cell = inflater.inflate(R.layout.pic_holder_item, container, false)
        return PicHolder(cell)
    }

    override fun onBindViewHolder(holder: PicHolder, position: Int) {

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

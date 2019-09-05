package com.example.fourinoneapp.views.utils

import android.util.Log
import androidx.recyclerview.widget.DiffUtil
import com.example.fourinoneapp.models.ImageExporter

class ImagesDiffUtilCallback(private val oldList: List<ImageExporter>, private val newList: List<ImageExporter>) : DiffUtil.Callback() {
    override fun getOldListSize() : Int{
        Log.d("oldList Size", oldList.size.toString())
        return oldList.size
    }

    override fun getNewListSize() : Int{
        Log.d("newList Size", newList.size.toString())
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) = oldList[oldItemPosition].imageFacer == newList[newItemPosition].imageFacer

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) = true
}
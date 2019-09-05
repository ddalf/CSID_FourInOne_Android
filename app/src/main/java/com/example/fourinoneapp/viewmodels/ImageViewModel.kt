package com.example.fourinoneapp.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.fourinoneapp.models.ImageExporter
import io.reactivex.Completable

class ImageViewModel: ViewModel(){
    val originalImages : ArrayList<ImageExporter> = ArrayList()
    val filterdImages : ArrayList<ImageExporter> = ArrayList()
    val oldfilteredImages : ArrayList<ImageExporter> = ArrayList()

    fun search(query: String): Completable = Completable.create {
        Log.d("search fun", query)
        val wanted = originalImages.filter {
            it.imageTXT!!.toLowerCase().contains(query.toLowerCase())
        }.toList()

        Log.d("filtered", wanted.toString())
        Log.d("filtered", wanted.size.toString())
        filterdImages.clear()
        filterdImages.addAll(wanted)
        it.onComplete()
    }
}